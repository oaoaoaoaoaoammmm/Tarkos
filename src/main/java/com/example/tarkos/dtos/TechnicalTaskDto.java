package com.example.tarkos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Technical task dto")
public class TechnicalTaskDto {
    private Integer id;
    private Boolean ready;
    private String description;
    private LocalDate date;
    private List<TaskDto> tasks;
}
