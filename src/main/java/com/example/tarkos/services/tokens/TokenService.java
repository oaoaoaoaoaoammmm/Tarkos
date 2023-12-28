package com.example.tarkos.services.tokens;

public interface TokenService {
    String generateToken(String username);
    String getUsername(String token);
    boolean checkExpiration(String token);
}
