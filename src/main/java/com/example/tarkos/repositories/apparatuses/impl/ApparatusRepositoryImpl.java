package com.example.tarkos.repositories.apparatuses.impl;

import com.example.tarkos.models.Apparatus;
import com.example.tarkos.models.Task;
import com.example.tarkos.repositories.apparatuses.ApparatusRepository;
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
public class ApparatusRepositoryImpl implements ApparatusRepository {

    private final DataSource dataSource;

    public ApparatusRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Collection<Apparatus> getApparatuses() {
        List<Apparatus> apparatuses = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                                        
                    select apparatuses_types.id,
                           apparatuses.apparatus_type,
                           apparatuses_types.name,
                           apparatuses_types.volume,
                           apparatuses_types.description
                    from apparatuses_types
                             join apparatuses on apparatuses_types.apparatus_id = apparatuses.id;
                    """);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            select apparatuses_types.id,
                                   apparatuses.apparatus_type,
                                   apparatuses_types.name,
                                   apparatuses_types.volume,
                                   apparatuses_types.description
                            from apparatuses_types
                                     join apparatuses on apparatuses_types.apparatus_id = apparatuses.id;
                            """)
            ) {
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
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return apparatuses;
    }

    public Collection<String> getApparatusModsById(Integer id) {

        List<String> list = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                                        
                    select mods.mode_name
                    from apparatuses_modes
                             join mods on apparatuses_modes.mode_id = mods.id
                    where apparatuses_modes.apparatus_type_id = {};
                    """, id);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                            select mods.mode_name
                            from apparatuses_modes
                                     join mods on apparatuses_modes.mode_id = mods.id
                            where apparatuses_modes.apparatus_type_id = ?;
                            """)
            ) {
                statement.setInt(1, id);

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

    public Collection<String> getAllOperationsTypes() {
        List<String> list = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                                        
                    select apparatus_type from apparatuses;
                    """);
            try (
                    PreparedStatement statement = con.prepareStatement("""
                            select apparatus_type from apparatuses;
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

    public void addApparatusesTasks(List<Task> tasks) {
        try (Connection con = dataSource.getConnection()) {
            try (
                    PreparedStatement statement = con.prepareStatement("""
                            insert into apparatuses_tasks (apparatus_type_id, task_id)
                            VALUES ((select id from apparatuses_types where name = ?), ?);
                            """)
            ) {

                for (Task task : tasks) {
                    for (Apparatus apparatus : task.getApparatuses()) {
                        statement.setString(1, apparatus.getName());
                        statement.setInt(2, task.getId());
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
