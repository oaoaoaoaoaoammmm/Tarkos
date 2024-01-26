package com.example.tarkos.models;


import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalTask {
    private Integer id;
    private Boolean ready;
    private String description;
    private LocalDate date;
    private List<Task> tasks;
}
