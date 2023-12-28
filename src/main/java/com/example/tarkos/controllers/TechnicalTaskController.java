package com.example.tarkos.controllers;


import com.example.tarkos.dtos.TechnicalTaskDto;
import com.example.tarkos.services.technicaltasks.TechnicalTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/technical-tasks")
@Tag(
        name = "Technical task controller",
        description = "All about technical task."
)
public class TechnicalTaskController {

    private final TechnicalTaskService technicalTaskService;

    public TechnicalTaskController(
            TechnicalTaskService technicalTaskService
    ) {
        this.technicalTaskService = technicalTaskService;
    }

    @Operation(summary = "Find technical task by date")
    @GetMapping
    public ResponseEntity<TechnicalTaskDto> findTechnicalTaskByDate(
            @RequestParam
            @NotNull
            @Parameter(description = "Technical task's date, format \"yyyy-MM-dd\"", required = true)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate date
    ) {

        TechnicalTaskDto technicalTaskDto = technicalTaskService.findTechnicalTaskByDate(date);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(technicalTaskDto);
    }

    @Operation(summary = "Set ready flag on technical task")
    @PutMapping
    public ResponseEntity<TechnicalTaskDto> passTechnicalTaskByDate(
            @RequestParam
            @NotNull
            @Parameter(description = "Technical task's date, format \"yyyy-MM-dd\"", required = true)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate date
    ) {
        technicalTaskService.passTechnicalTaskByDate(date);

        return ResponseEntity
                .noContent()
                .build();
    }

    @Operation(summary = "Add technical task")
    @PostMapping
    public ResponseEntity<?> addTechnicalTask (
            @RequestBody
            @NotNull
            @Parameter(description = "Technical task dto", required = true)
            TechnicalTaskDto technicalTaskDto
    ) {

        technicalTaskService.addTechnicalTask(technicalTaskDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
