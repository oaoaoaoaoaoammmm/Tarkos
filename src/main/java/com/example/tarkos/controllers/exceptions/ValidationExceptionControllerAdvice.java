package com.example.tarkos.controllers.exceptions;


import com.example.tarkos.controllers.exceptions.responses.ValidationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Order(1)
@ControllerAdvice
public class ValidationExceptionControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> onConstraintValidationException(ConstraintViolationException ex) {
        List<ValidationException> exceptions = ex.getConstraintViolations().stream()
                .map(
                        error -> ValidationException.builder()
                                .fieldName(error.getPropertyPath().toString())
                                .message(error.getMessage())
                                .build()
                )
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptions);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ValidationException> exceptions = ex.getBindingResult().getFieldErrors().stream()
                .map(
                        error -> ValidationException.builder()
                                .fieldName(error.getField())
                                .message(error.getDefaultMessage())
                                .build()
                )
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptions);
    }
}
