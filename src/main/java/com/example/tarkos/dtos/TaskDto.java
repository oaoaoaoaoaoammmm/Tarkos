package com.example.tarkos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Task dto")
public class TaskDto {
    private Integer id;
    private List<ApparatusDto> apparatuses;
    private List<ProductDto> products;
}
