package com.example.tarkos.services.technicaltasks;

import com.example.tarkos.dtos.TechnicalTaskDto;

import java.time.LocalDate;

public interface TechnicalTaskService {
    TechnicalTaskDto findTechnicalTaskByDate(LocalDate date);
    void passTechnicalTaskByDate(LocalDate date);
    void addTechnicalTask(TechnicalTaskDto technicalTaskDto);
}
