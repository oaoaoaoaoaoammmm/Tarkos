package com.example.tarkos.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String type;
    private String name;
    private Double variety;
    private Double amount;
    private Double requiredAmount;
    private String description;
}
