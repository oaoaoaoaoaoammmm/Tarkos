package com.example.tarkos.controllers;


import com.example.tarkos.dtos.OperationDto;
import com.example.tarkos.services.operations.OperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/operations")
@Tag(
        name = "Operation controller",
        description = "All about operations."
)
public class OperationController {

    private final OperationService operationService;

    public OperationController(
            OperationService operationService
    ) {
        this.operationService = operationService;
    }

    @Operation(summary = "All factory operation")
    @PutMapping("/make-beer")
    public ResponseEntity<?> makeBeer(
            @RequestBody
            @NotNull
            @Parameter(description = "Operation describe", required = true)
            OperationDto operation
    ) {

        operationService.makeBeer(
                operation.getOperationType(),
                operation.getApparatusName(),
                operation.getModeName(),
                operation.getPairs()
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
