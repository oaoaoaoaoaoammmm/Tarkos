package com.example.tarkos.controllers.exceptions.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DefaultException {
    private String message;
    private String description;
    private LocalDateTime time;
}
