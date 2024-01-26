package com.example.tarkos.repositories.technicaltasks.impl;

import com.example.tarkos.models.TechnicalTask;
import com.example.tarkos.repositories.technicaltasks.TechnicalTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Repository
public class TechnicalTaskRepositoryImpl implements TechnicalTaskRepository {

    private final DataSource dataSource;

    public TechnicalTaskRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<TechnicalTask> findTechnicalTaskByDate(LocalDate date) {

        TechnicalTask technicalTask = null;

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                                        
                    select *
                    from technical_tasks
                    where work_day_date = {};
                    """, date.toString());

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            select *
                            from technical_tasks
                            where work_day_date = ?;
                            """)
            ) {
                statement.setDate(1, Date.valueOf(date));
                try (ResultSet result = statement.executeQuery()) {

                    if (result.next()) {
                        technicalTask = TechnicalTask.builder()
                                .id(result.getInt(1))
                                .ready(result.getBoolean(2))
                                .date(result.getDate(3).toLocalDate())
                                .description(result.getString(4))
                                .build();
                    }
                }
            }

        } catch (SQLException ex) {
            log.debug("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return Optional.ofNullable(technicalTask);
    }

    public void UpdateTechnicalTaskReadyByDate(LocalDate date) {

        try (Connection con = dataSource.getConnection()) {

            log.info("""
                                        
                    update technical_tasks
                    set ready = true
                    where work_day_date = ?;
                    """);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            update technical_tasks
                            set ready = true
                            where work_day_date = ?;
                            """)
            ) {
                statement.setDate(1, Date.valueOf(date));

                if (statement.executeUpdate() == 0) {
                    log.warn("Didn't update technical tasks ready by date - {}", date);
                    throw new RuntimeException("Technical tasks didn't update");
                }
            }
        } catch (SQLException ex) {
            log.debug("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
    }

    public void addTechnicalTask(TechnicalTask technicalTask) {
        try (Connection con = dataSource.getConnection()) {

            log.info("""
                                        
                    insert into technical_tasks (ready, work_day_date, description)
                    values ({}, {}, {});
                    """, technicalTask.getReady(), technicalTask.getDate(), technicalTask.getDescription());

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            insert into technical_tasks (ready, work_day_date, description)
                            values (?, ?, ?);
                            """, PreparedStatement.RETURN_GENERATED_KEYS)
            ) {
                statement.setBoolean(1, technicalTask.getReady());
                statement.setDate(2, Date.valueOf(technicalTask.getDate()));
                statement.setString(3, technicalTask.getDescription());

                statement.executeUpdate();
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next())
                        technicalTask.setId(result.getInt(1));

                }
            }

            System.out.println("aaa");

        } catch (SQLException ex) {
            log.debug("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }
    }
}