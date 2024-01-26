package com.example.tarkos.repositories.users.impl;

import com.example.tarkos.models.User;
import com.example.tarkos.repositories.users.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<User> findByUsername(String username) {

        User user = null;

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                    
                    select "user".id, posts.profession, first_name, last_name, age, username, password from "user"
                    join posts on "user".id = posts.user_id
                    where username = {};
                    """, username);

            try (
                    PreparedStatement statement = con.prepareStatement("""            
                            select "user".id, posts.profession, first_name, last_name, age, username, password from "user"
                            join posts on "user".id = posts.user_id
                            where username = ?;
                            """)
            ) {
                statement.setString(1, username);
                try (ResultSet result = statement.executeQuery()) {

                    if (result.next()) {
                        user = User.builder()
                                .id(result.getInt(1))
                                .profession(result.getString(2))
                                .firstName(result.getString(3))
                                .lastName(result.getString(4))
                                .age(result.getInt(5))
                                .username(result.getString(6))
                                .password(result.getString(7))
                                .build();
                    }
                }
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return Optional.ofNullable(user);
    }

    public Optional<String> findProfessionByUsername(String username) {

        String profession = null;

        try (Connection con = dataSource.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            log.info("""
                    select posts.profession from "user"
                    join posts on "user".id = posts.user_id
                    where username = {};
                    """, username);

            try (
                    PreparedStatement statement = con.prepareStatement("""
                             select posts.profession from "user"
                                                join posts on "user".id = posts.user_id
                                                where username = ?;
                            """)
            ) {
                statement.setString(1, username);
                try (ResultSet result = statement.executeQuery()) {

                    if (result.next()) {
                        profession = result.getString(1);
                    }
                }
            }

        } catch (SQLException ex) {
            log.error("SQL exception - {}", ex.getMessage());
            throw new NoSuchElementException("Something went wrong");
        }

        return Optional.ofNullable(profession);
    }

}
