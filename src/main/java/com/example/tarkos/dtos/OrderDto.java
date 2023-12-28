package com.example.tarkos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Order dto")
public class OrderDto {
    private Integer id;
    private String owner;
    private String executor;
    private String productName;
    private Double count;
    private String description;
    private Boolean ready;
}
