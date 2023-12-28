package com.example.tarkos.controllers.exceptions;


import com.example.tarkos.controllers.exceptions.responses.DefaultException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


@Order(2)
@ControllerAdvice
public class RuntimeExceptionControllerAdvice {

    @Order(1)
    @ExceptionHandler(AuthorizationServiceException.class)
    public ResponseEntity<?> onAuthorizationServiceException(AuthorizationServiceException ex) {
        DefaultException exception = DefaultException.builder()
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception);
    }


    @Order(2)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> onRuntimeException(RuntimeException ex) {
        DefaultException exception = DefaultException.builder()
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception);
    }
}
