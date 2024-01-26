package com.example.tarkos.controllers.exceptions.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationException {
    private final String fieldName;
    private final String message;
}