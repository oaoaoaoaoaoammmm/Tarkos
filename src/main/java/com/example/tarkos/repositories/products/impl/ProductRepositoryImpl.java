package com.example.tarkos.repositories.products.impl;


import com.example.tarkos.models.Product;
import com.example.tarkos.models.Task;
import com.example.tarkos.repositories.products.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final DataSource dataSource;

    public ProductRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Collection<Product> getProducts() {

        List<Product> products = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                                        
                    select products_types.id,
                           products.product_type,
                           products_types.name,
                           products_types.variety,
                           products_types.count,
                           products_types.description
                    from products_types
                             join products on products_types.product_id = products.id;
                    """);

            try (PreparedStatement statement = con.prepareStatement("""
                            select products_types.id,
                                   products.product_type,
                                   products_types.name,
                                   products_types.variety,
                                   products_types.count,
                                   products_types.description
                            from products_types
                                     join products on products_types.product_id = products.id;
                    """)
            ) {
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        products.add(
                                Product.builder()
                                        .id(result.getInt(1))
                                        .type(result.getString(2))
                                        .name(result.getString(3))
                                        .variety(result.getDouble(4))
                                        .amount(result.getDouble(5))
                                        .requiredAmount(0.0)
                                        .description(result.getString(6))
                                        .build()
                        );
                    }
                }
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return products;
    }

    public Collection<String> getProductsTypes() {
        List<String> list = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                                        
                    select product_type from products;
                    """);
            try (
                    PreparedStatement statement = con.prepareStatement("""
                            select product_type from products;
                            """)
            ) {
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        list.add(result.getString(1));
                    }
                }
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
        return list;
    }

    public void updateCountProductByName(String name, Double count) {

        try (Connection con = dataSource.getConnection()) {

            log.info("""
                                        
                    update products_types
                    set count = count + {}
                    where name = {};
                    """, count, name);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            update products_types
                            set count = count + ?
                            where name = ?;
                            """)
            ) {
                statement.setDouble(1, count);
                statement.setString(2, name);

                if (statement.executeUpdate() == 0) {
                    log.warn("Didn't update product");
                    throw new RuntimeException("Water didn't prepare");
                }
            }
        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
    }

    public Collection<String> getProductNameByDerivativeProductName(String derivativeProductName) {
        List<String> list = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                                        
                    select products_types.name
                    from products_types
                             left join products_derivatives on products_types.id = products_derivatives.product_id
                    where derivatives_product_id = (select id from products_types where name = {});
                    """, derivativeProductName);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            select products_types.name
                            from products_types
                                     left join products_derivatives on products_types.id = products_derivatives.product_id
                            where derivatives_product_id = (select id from products_types where name = ?);
                            """)
            ) {
                statement.setString(1, derivativeProductName);
                try (ResultSet result = statement.executeQuery()) {

                    while (result.next()) {
                        list.add(result.getString(1));
                    }
                }
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
        return list;
    }

    public void addProductsTasks(List<Task> tasks) {

        try (Connection con = dataSource.getConnection()) {
            try (
                    PreparedStatement statement = con.prepareStatement("""
                            insert into products_tasks (product_type_id, task_id, count)
                            VALUES ((select id from products_types where name = ?), ?, ?);
                            """)
            ) {
                for (Task task : tasks) {
                    for (Product product : task.getProducts()) {
                        statement.setString(1, product.getName());
                        statement.setInt(2, task.getId());
                        statement.setDouble(3, product.getAmount());
                        statement.addBatch();
                    }
                }
                statement.executeBatch();
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
    }
}
