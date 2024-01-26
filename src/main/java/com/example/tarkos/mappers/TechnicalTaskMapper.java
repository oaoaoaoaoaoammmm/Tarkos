package com.example.tarkos.mappers;


import com.example.tarkos.dtos.TechnicalTaskDto;
import com.example.tarkos.models.TechnicalTask;
import org.springframework.stereotype.Component;

@Component
public class TechnicalTaskMapper {

    private final TaskMapper taskMapper;

    public TechnicalTaskMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }


    public TechnicalTaskDto convertToTechnicalTaskDto(TechnicalTask technicalTask) {
        if (technicalTask == null) {
            return null;
        }

        TechnicalTaskDto.TechnicalTaskDtoBuilder dto = TechnicalTaskDto.builder();

        return dto.id(technicalTask.getId())
                .ready(technicalTask.getReady())
                .description(technicalTask.getDescription())
                .date(technicalTask.getDate())
                .build();
    }

    public TechnicalTask convertToTechnicalTask(TechnicalTaskDto dto) {
        if (dto == null) {
            return null;
        }

        TechnicalTask.TechnicalTaskBuilder tt = TechnicalTask.builder();

        return tt.ready(dto.getReady())
                .description(dto.getDescription())
                .date(dto.getDate())
                .tasks(
                        dto.getTasks().stream()
                                .map(taskMapper::convertToTask)
                                .toList()
                )
                .build();
    }
}
