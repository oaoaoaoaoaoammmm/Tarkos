package com.example.tarkos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.Collection;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Operation dto")
public class OperationDto {
    private String operationType;
    private String apparatusName;
    private String modeName;
    private Collection<Pair<String, Double>> pairs;
}

