package com.example.tarkos.repositories.operations.impl;

import com.example.tarkos.repositories.operations.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
@Repository
public class OperationRepositoryImpl implements OperationRepository {
    private final DataSource dataSource;

    public OperationRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertApparatusOperationByName(String apparatusName) {

        try (Connection con = dataSource.getConnection()) {

            log.info("""
                                        
                    insert into apparatuses_operations (apparatus_type_id, date)
                    select id, now()
                    from apparatuses_types
                    where name = {};
                    """, apparatusName);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            insert into apparatuses_operations (apparatus_type_id, date)
                            select id, now()
                            from apparatuses_types
                            where name = ?;
                            """)
            ) {
                statement.setString(1, apparatusName);

                if (statement.executeUpdate() == 0) {
                    log.warn("Didn't record apparatus operation with name - {}", apparatusName);
                    throw new RuntimeException("Operation didn't record");
                }

            }
        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
    }

    public void insertModInLastOperationByName(String modeName) {
        try (Connection con = dataSource.getConnection()) {

            log.info("""
                                        
                    insert into operations_modes (operation_id, mode_id)
                    VALUES ((select max(id) from apparatuses_operations), (select id
                                                                           from mods
                                                                           where mode_name = {}));
                    """, modeName);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            insert into operations_modes (operation_id, mode_id)
                            VALUES (
                            (select max(id) from apparatuses_operations), (select id
                                                                           from mods
                                                                           where mode_name = ?));
                            """)
            ) {
                statement.setString(1, modeName);

                if (statement.executeUpdate() == 0) {
                    log.warn("Didn't record operation mode with name - {}", modeName);
                    throw new RuntimeException("Mode didn't record");
                }
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
    }

    public void insertApparatusProductsInLastOperation(String productName, Double count) {

        try (Connection con = dataSource.getConnection()) {

            log.info("""
                                        
                    insert into operations_apparatuses_products (product_type_id, apparatus_operation_id, count, description)
                    VALUES ((select id
                             from products_types
                             where name = {}), (select max(id) from apparatuses_operations), {}, '');
                    """, productName, count);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            insert into operations_apparatuses_products (product_type_id, apparatus_operation_id, count, description)
                            VALUES ((select id
                                     from products_types
                                     where name = ?), (select max(id) from apparatuses_operations), ?, '');
                            """)
            ) {
                statement.setString(1, productName);
                statement.setDouble(2, count);

                if (statement.executeUpdate() == 0) {
                    log.warn("Didn't record product apparatus in last operation, productName - {}, count - {}", productName, count);
                    throw new RuntimeException("Product apparatus in last operation didn't record");
                }
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
    }
}
