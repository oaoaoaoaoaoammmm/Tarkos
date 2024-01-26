package com.example.tarkos.controllers;


import com.example.tarkos.dtos.ApparatusDto;
import com.example.tarkos.services.apparatuses.ApparatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Validated
@RestController
@RequestMapping("/apparatuses")
@Tag(
        name = "Apparatus controller",
        description = "All about apparatus."
)
public class ApparatusController {

    private final ApparatusService apparatusService;

    public ApparatusController(ApparatusService apparatusService) {
        this.apparatusService = apparatusService;
    }


    @Operation(summary = "Get all apparatuses")
    @GetMapping
    public ResponseEntity<Collection<ApparatusDto>> getApparatus(

    ) {
        Collection<ApparatusDto> apparatuses = apparatusService.getApparatus();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apparatuses);
    }

    @Operation(summary = "Get all apparatus mods by id")
    @GetMapping("/{apparatus_id}/mods")
    public ResponseEntity<Collection<String>> getApparatusModsById(
            @PathVariable
            @NotNull
            @Min(1)
            @Parameter(description = "Apparatus id", required = true)
            Integer apparatus_id
    ) {
        Collection<String> mods = apparatusService.getApparatusModsById(apparatus_id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mods);
    }

    @Operation(summary = "Get all operation's types")
    @GetMapping("/types")
    public ResponseEntity<Collection<String>> getAllOperationsTypes(

    ) {
        Collection<String> types = apparatusService.getAllOperationsTypes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(types);
    }
}
