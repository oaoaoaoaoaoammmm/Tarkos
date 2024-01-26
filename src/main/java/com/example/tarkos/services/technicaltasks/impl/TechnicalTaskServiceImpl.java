package com.example.tarkos.services.technicaltasks.impl;


import com.example.tarkos.dtos.TaskDto;
import com.example.tarkos.dtos.TechnicalTaskDto;
import com.example.tarkos.mappers.TaskMapper;
import com.example.tarkos.mappers.TechnicalTaskMapper;
import com.example.tarkos.models.TechnicalTask;
import com.example.tarkos.repositories.apparatuses.ApparatusRepository;
import com.example.tarkos.repositories.products.ProductRepository;
import com.example.tarkos.repositories.tasks.TaskRepository;
import com.example.tarkos.repositories.technicaltasks.TechnicalTaskRepository;
import com.example.tarkos.services.technicaltasks.TechnicalTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class TechnicalTaskServiceImpl implements TechnicalTaskService {

    private final TechnicalTaskRepository technicalTaskRepository;

    private final TaskRepository taskRepository;

    private final ProductRepository productRepository;
    private final ApparatusRepository apparatusRepository;

    private final TechnicalTaskMapper technicalTaskMapper;

    private final TaskMapper taskMapper;

    public TechnicalTaskServiceImpl(
            TechnicalTaskRepository technicalTaskRepository,
            TaskRepository taskRepository,
            ProductRepository productRepository, ApparatusRepository apparatusRepository, TechnicalTaskMapper technicalTaskMapper,
            TaskMapper taskMapper
    ) {
        this.technicalTaskRepository = technicalTaskRepository;
        this.taskRepository = taskRepository;
        this.productRepository = productRepository;
        this.apparatusRepository = apparatusRepository;
        this.technicalTaskMapper = technicalTaskMapper;
        this.taskMapper = taskMapper;
    }

    public TechnicalTaskDto findTechnicalTaskByDate(LocalDate date) {

        log.info("Search technical task by date - {}", date.toString());

        TechnicalTask technicalTask = technicalTaskRepository
                .findTechnicalTaskByDate(date)
                .orElseThrow(() -> new NoSuchElementException("No such technical task by date"));

        List<TaskDto> tasks = taskRepository
                .findAllTasksByTechnicalTaskId(technicalTask.getId())
                .stream()
                .map(taskMapper::convertToTaskDto)
                .toList();

        TechnicalTaskDto technicalTaskDto = technicalTaskMapper.convertToTechnicalTaskDto(technicalTask);
        technicalTaskDto.setTasks(tasks);

        return technicalTaskDto;
    }

    public void passTechnicalTaskByDate(LocalDate date) {
        log.info("Passing technical task by date - {}", date.toString());

        technicalTaskRepository.UpdateTechnicalTaskReadyByDate(date);
    }

    public void addTechnicalTask(TechnicalTaskDto technicalTaskDto) {

        log.info("Adding technical task");

        TechnicalTask technicalTask = technicalTaskMapper.convertToTechnicalTask(technicalTaskDto);

        technicalTaskRepository.addTechnicalTask(technicalTask);
        taskRepository.addTasks(technicalTask);
        productRepository.addProductsTasks(technicalTask.getTasks());
        apparatusRepository.addApparatusesTasks(technicalTask.getTasks());
    }
}
