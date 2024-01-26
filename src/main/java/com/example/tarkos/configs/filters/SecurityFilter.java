package com.example.tarkos.configs.filters;


import com.example.tarkos.services.tokens.TokenService;
import io.jsonwebtoken.ClaimJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public SecurityFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String token = request.getHeader("Token");

        try {
            if (token != null && tokenService.checkExpiration(token)) {

                String username = tokenService.getUsername(token);
                Authentication authentication = UsernamePasswordAuthenticationToken
                        .authenticated(username, "", Collections.singleton((GrantedAuthority) () -> "USER"));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ClaimJwtException ex) {
            log.info("Token expired");
        }

        filterChain.doFilter(request, response);
    }
}
