package com.example.tarkos.repositories.tasks;

import com.example.tarkos.models.Task;
import com.example.tarkos.models.TechnicalTask;

import java.util.Collection;

public interface TaskRepository {
    Collection<Task> findAllTasksByTechnicalTaskId(Integer id);
    void addTasks(TechnicalTask technicalTask);
}
