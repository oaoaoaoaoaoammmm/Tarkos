package com.example.tarkos.repositories.orders.impl;

import com.example.tarkos.models.Order;
import com.example.tarkos.repositories.orders.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final DataSource dataSource;

    public OrderRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Collection<String> getAllExecutors() {
        List<String> executors = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {

            log.info("""

                    select posts.profession
                    from posts
                    where profession != 'MANAGER';
                    """);

            PreparedStatement statement = con.prepareStatement("""
                    select posts.profession
                    from posts
                    where profession != 'MANAGER';
                    """);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                executors.add(result.getString(1));
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return executors;
    }

    public Collection<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {

            log.info("""
                                        
                    select products_orders.id,
                           (select posts.profession from posts where posts.id = products_orders.owner_id) as owner,
                           (select posts.profession from posts where posts.id = products_orders.executor_id) as executor,
                           products_types.name,
                           products_orders.count,
                           products_orders.description,
                           products_orders.ready
                    from products_orders
                    join posts on products_orders.executor_id = posts.id
                    join products_types on products_types.id = products_orders.product_type_id;
                    """);

            try (PreparedStatement statement = con.prepareStatement("""
                    select products_orders.id,
                           (select posts.profession from posts where posts.id = products_orders.owner_id) as owner,
                           (select posts.profession from posts where posts.id = products_orders.executor_id) as executor,
                           products_types.name,
                           products_orders.count,
                           products_orders.description,
                           products_orders.ready
                    from products_orders
                    join posts on products_orders.executor_id = posts.id
                    join products_types on products_types.id = products_orders.product_type_id;
                    """)) {

                packOrders(orders, statement);
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
        return orders;
    }

    private void packOrders(List<Order> orders, PreparedStatement statement) throws SQLException {
        try (ResultSet result = statement.executeQuery()){
            while (result.next()) {
                orders.add(
                        Order.builder()
                                .id(result.getInt(1))
                                .owner(result.getString(2))
                                .executor(result.getString(3))
                                .productName(result.getString(4))
                                .count(result.getDouble(5))
                                .description(result.getString(6))
                                .ready(result.getBoolean(7))
                                .build()
                );
            }
        }
    }

    public Collection<Order> findActiveOrdersByProfession(String profession) {

        List<Order> orders = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {

            log.info("""
                       
                    select products_orders.id,
                           (select posts.profession from posts where posts.id = products_orders.owner_id) as owner,
                           (select posts.profession from posts where posts.id = products_orders.executor_id) as executor,
                           products_types.name,
                           products_orders.count,
                           products_orders.description,
                           products_orders.ready
                    from products_orders
                    join posts on products_orders.executor_id = posts.id
                    join products_types on products_types.id = products_orders.product_type_id
                    where posts.profession = {};
                    """, profession);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            select products_orders.id,
                                   (select posts.profession from posts where posts.id = products_orders.owner_id) as owner,
                                   (select posts.profession from posts where posts.id = products_orders.executor_id) as executor,
                                   products_types.name,
                                   products_orders.count,
                                   products_orders.description,
                                   products_orders.ready
                            from products_orders
                            join posts on products_orders.executor_id = posts.id
                            join products_types on products_types.id = products_orders.product_type_id
                            where text(posts.profession) = ?;
                            """)
            ) {
                statement.setString(1, profession);
                packOrders(orders, statement);
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return orders;
    }

    public Optional<Order> findOrderById(Integer id) {
        Order order = null;

        try (Connection con = dataSource.getConnection()) {

            log.info("""
                                        
                    select *
                    from products_orders
                    where id = {}
                    """, id);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            select products_orders.id,
                                   products_types.name,
                                   products_orders.count
                            from products_orders
                            join products_types on products_types.id = products_orders.product_type_id
                            where products_orders.id = ?
                            """)
            ) {
                statement.setInt(1, id);
                try (ResultSet result = statement.executeQuery()) {

                    if (result.next()) {
                        order = Order.builder()
                                .id(result.getInt(1))
                                .productName(result.getString(2))
                                .count(result.getDouble(3))
                                .build();
                    }
                }
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return Optional.ofNullable(order);
    }


    public void updateReadyOrderById(Integer id) {

        try (Connection con = dataSource.getConnection()) {

            log.info("""
                                        
                    update products_orders
                            set ready = true
                            where id = {}
                            """, id);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            update products_orders
                            set ready = true
                            where id = ?;
                            """)
            ) {
                statement.setInt(1, id);

                if (statement.executeUpdate() == 0) {
                    log.warn("Orders didn't execute by id - {}", id);
                    throw new NoSuchElementException("Orders didn't execute");
                }
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
    }

    @Override
    public Order addOrder(Order order) {

        try (Connection con = dataSource.getConnection()) {

            log.info("""
                    insert into products_orders (executor_id, owner_id, product_type_id, count, description)
                            VALUES (
                            (select id from posts where text(profession) = ?),
                            (select id from posts where text(profession) = ?),
                            (select id from products_types where name = ?),
                             ?, ?
                            );
                    """);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            insert into products_orders (executor_id, owner_id, product_type_id, count, description)
                            VALUES (
                            (select id from posts where text(profession) = ?),
                            (select id from posts where text(profession) = ?),
                            (select id from products_types where name = ?),
                             ?, ?
                            );
                            """, PreparedStatement.RETURN_GENERATED_KEYS)
            ) {
                statement.setString(1, order.getExecutor());
                statement.setString(2, order.getOwner());
                statement.setString(3, order.getProductName());
                statement.setDouble(4, order.getCount());
                statement.setString(5, order.getDescription());
                statement.executeUpdate();

                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        order.setId(result.getInt(1));
                    }
                }
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return order;
    }
}
