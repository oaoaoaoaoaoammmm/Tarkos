package com.example.tarkos.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Integer id;
    private List<Apparatus> apparatuses;
    private List<Product> products;
}
