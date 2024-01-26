package com.example.tarkos.repositories.technicaltasks;

import com.example.tarkos.models.TechnicalTask;

import java.time.LocalDate;
import java.util.Optional;

public interface TechnicalTaskRepository {
    Optional<TechnicalTask> findTechnicalTaskByDate(LocalDate date);
    void UpdateTechnicalTaskReadyByDate(LocalDate date);
    void addTechnicalTask(TechnicalTask technicalTask);
}
