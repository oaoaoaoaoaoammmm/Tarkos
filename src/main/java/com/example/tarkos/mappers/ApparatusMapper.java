package com.example.tarkos.mappers;


import com.example.tarkos.dtos.ApparatusDto;
import com.example.tarkos.models.Apparatus;
import org.springframework.stereotype.Component;

@Component
public class ApparatusMapper {

    public ApparatusDto convertToApparatusDto(Apparatus apparatus) {
        if (apparatus == null) {
            return null;
        }

        ApparatusDto.ApparatusDtoBuilder dto = ApparatusDto.builder();

        return dto.id(apparatus.getId())
                .type(apparatus.getType())
                .name(apparatus.getName())
                .volume(apparatus.getVolume())
                .description(apparatus.getDescription())
                .build();
    }

    public Apparatus convertToApparatus(ApparatusDto dto) {
        if (dto == null) {
            return null;
        }

        Apparatus.ApparatusBuilder apparatus = Apparatus.builder();

        return apparatus.name(dto.getName())
                .build();
    }
}
