package com.example.tarkos.services.apparatuses;

import com.example.tarkos.dtos.ApparatusDto;

import java.util.Collection;

public interface ApparatusService {
    Collection<ApparatusDto> getApparatus();
    Collection<String> getApparatusModsById(Integer id);
    Collection<String> getAllOperationsTypes();
}
