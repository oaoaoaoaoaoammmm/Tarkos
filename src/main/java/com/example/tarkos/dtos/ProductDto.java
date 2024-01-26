package com.example.tarkos.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Product dto")
public class ProductDto {
    private Integer id;
    private String type;
    private String name;
    private Double variety;
    private Double amount;
    private Double requiredAmount;
    private String description;
}
