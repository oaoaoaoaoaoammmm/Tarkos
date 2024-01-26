package com.example.tarkos.repositories.tasks.impl;


import com.example.tarkos.models.Apparatus;
import com.example.tarkos.models.Product;
import com.example.tarkos.models.Task;
import com.example.tarkos.models.TechnicalTask;
import com.example.tarkos.repositories.tasks.TaskRepository;
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
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSource dataSource;

    public TaskRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Collection<Task> findAllTasksByTechnicalTaskId(Integer id) {

        List<Task> tasks = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                                        
                    select *
                    from tasks
                    where technical_task_id = {};
                    """, id);

            PreparedStatement statement = con.prepareStatement("""
                    select *
                    from tasks
                    where technical_task_id = ?;
                    """);

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                tasks.add(
                        Task.builder()
                                .id(result.getInt(1))
                                .apparatuses(findAllApparatusesByTaskId(result.getInt(1)))
                                .products(findAllProductsByTaskId(result.getInt(1)))
                                .build()
                );
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return tasks;
    }

    private List<Apparatus> findAllApparatusesByTaskId(Integer id) {

        List<Apparatus> apparatuses = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                                    
                    select tasks.id,
                           apparatuses_types.id,
                           apparatuses.apparatus_type,
                           apparatuses_types.name,
                           apparatuses_types.description
                    from tasks
                             join apparatuses_tasks on tasks.id = apparatuses_tasks.task_id
                             join apparatuses_types on apparatuses_tasks.apparatus_type_id = apparatuses_types.id
                             join apparatuses on apparatuses_types.apparatus_id = apparatuses.id
                    where tasks.id = {}
                    group by tasks.id, apparatuses_types.id, apparatuses_types.name, apparatuses_types.description,
                             apparatuses.apparatus_type;
                    """, id);

            try (
                    PreparedStatement statement = con.prepareStatement("""     
                                select apparatuses_types.id,
                                   apparatuses.apparatus_type,
                                   apparatuses_types.name,
                                   apparatuses_types.volume,
                                   apparatuses_types.description
                                                from tasks
                                     join apparatuses_tasks on tasks.id = apparatuses_tasks.task_id
                                     join apparatuses_types on apparatuses_tasks.apparatus_type_id = apparatuses_types.id
                                     join apparatuses on apparatuses_types.apparatus_id = apparatuses.id
                                                where tasks.id = ?
                            group by apparatuses_types.id, apparatuses_types.name, apparatuses_types.description, apparatuses_types.volume, apparatuses.apparatus_type
                            """
                    )
            ) {
                statement.setInt(1, id);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        apparatuses.add(
                                Apparatus.builder()
                                        .id(result.getInt(1))
                                        .type(result.getString(2))
                                        .name(result.getString(3))
                                        .volume(result.getInt(4))
                                        .description(result.getString(5))
                                        .build()
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            log.debug("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return apparatuses;
    }

    private List<Product> findAllProductsByTaskId(Integer id) {

        List<Product> products = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                                    
                    select products_types.id,
                           products.product_type,
                           products_types.name,
                           products_types.variety,
                           products_types.count,
                           products_tasks.count,
                           products_types.description
                    from tasks
                             join products_tasks on tasks.id = products_tasks.task_id
                             join products_types on products_tasks.product_type_id = products_types.id
                             join products on products_types.product_id = products.id
                    where tasks.id = {}
                    group by products_types.id, products_types.name, products_types.variety, products_types.count,
                             products_types.description,
                             products_tasks.count, products.product_type;
                    """, id);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                              
                                  select products_types.id,
                                     products.product_type,
                                     products_types.name,
                                     products_types.variety,
                                     products_types.count,
                                     products_tasks.count,
                                     products_types.description
                              from tasks
                                       join products_tasks on tasks.id = products_tasks.task_id
                                       join products_types on products_tasks.product_type_id = products_types.id
                                       join products on products_types.product_id = products.id
                              where tasks.id = ?
                              group by products_types.id, products_types.name, products_types.variety, products_types.count,
                                       products_types.description,
                                       products_tasks.count, products.product_type;
                            """)
            ) {
                statement.setInt(1, id);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        products.add(
                                Product.builder()
                                        .id(result.getInt(1))
                                        .type(result.getString(2))
                                        .name(result.getString(3))
                                        .variety(result.getDouble(4))
                                        .amount(result.getDouble(5))
                                        .requiredAmount(result.getDouble(6))
                                        .description(result.getString(7))
                                        .build()
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            log.debug("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return products;
    }

    public void addTasks(TechnicalTask technicalTask) {
        try (Connection con = dataSource.getConnection()) {

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            insert into tasks (technical_task_id) values (?)
                            """, PreparedStatement.RETURN_GENERATED_KEYS)
            ) {
                for (Task ignored : technicalTask.getTasks()) {
                    statement.setInt(1, technicalTask.getId());
                    statement.addBatch();
                }
                statement.executeBatch();
                Iterator<Task> it = technicalTask.getTasks().listIterator();
                try (ResultSet result = statement.getGeneratedKeys()) {
                    while (result.next()) {
                        it.next().setId(result.getInt(1));
                    }
                }
            }

        } catch (SQLException ex) {
            log.debug("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
    }
}
