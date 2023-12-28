package com.example.tarkos.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private String owner;
    private String executor;
    private String productName;
    private Double count;
    private String description;
    private Boolean ready;
}
