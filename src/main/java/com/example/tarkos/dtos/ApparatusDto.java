package com.example.tarkos.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Apparatus dto")
public class ApparatusDto {
    private Integer id;
    private String type;
    private String name;
    private Integer volume;
    private String description;
}
