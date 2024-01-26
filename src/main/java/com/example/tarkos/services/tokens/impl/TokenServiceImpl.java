package com.example.tarkos.services.tokens.impl;


import com.example.tarkos.props.keys.RsaKeyProperties;
import com.example.tarkos.services.tokens.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    private final RsaKeyProperties rsaKeys;

    public TokenServiceImpl(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        Claims claims = Jwts.claims().setSubject(username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.HOURS)))
                .signWith(SignatureAlgorithm.HS256, rsaKeys.privateKey().toString())
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(rsaKeys.privateKey().toString())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean checkExpiration(String token) {
        if (token == null) return false;

        return !Jwts.parser()
                .setSigningKey(rsaKeys.privateKey().toString())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(Date.from(Instant.now()));
    }
}
