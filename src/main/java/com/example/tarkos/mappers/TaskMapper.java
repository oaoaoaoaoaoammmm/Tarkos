package com.example.tarkos.mappers;


import com.example.tarkos.dtos.TaskDto;
import com.example.tarkos.models.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private final ApparatusMapper apparatusMapper;
    private final ProductMapper productMapper;

    public TaskMapper(ApparatusMapper apparatusMapper, ProductMapper productMapper) {
        this.apparatusMapper = apparatusMapper;
        this.productMapper = productMapper;
    }

    public TaskDto convertToTaskDto(Task task) {
        if (task == null) {
            return null;
        }

        TaskDto.TaskDtoBuilder dto = TaskDto.builder();

        return dto.id(task.getId())
                .apparatuses(
                        task.getApparatuses()
                                .stream()
                                .map(apparatusMapper::convertToApparatusDto)
                                .toList()
                )
                .products(
                        task.getProducts()
                                .stream()
                                .map(productMapper::convertToProductDto)
                                .toList()
                ).build();
    }

    public Task convertToTask(TaskDto dto) {
        if (dto == null) {
            return null;
        }

        Task.TaskBuilder task = Task.builder();

        return task.id(dto.getId())
                .apparatuses(
                        dto.getApparatuses()
                                .stream()
                                .map(apparatusMapper::convertToApparatus)
                                .toList()
                )
                .products(
                        dto.getProducts()
                                .stream()
                                .map(productMapper::convertToProduct)
                                .toList()
                ).build();
    }
}
