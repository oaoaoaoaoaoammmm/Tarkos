package com.example.tarkos.repositories.users;

import com.example.tarkos.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<String> findProfessionByUsername(String username);
}
