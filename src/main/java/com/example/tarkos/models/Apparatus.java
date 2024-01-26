package com.example.tarkos.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Apparatus {
    private Integer id;
    private String type;
    private String name;
    private Integer volume;
    private String description;
}
