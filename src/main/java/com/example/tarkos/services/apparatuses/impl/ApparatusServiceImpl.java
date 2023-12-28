package com.example.tarkos.services.apparatuses.impl;


import com.example.tarkos.dtos.ApparatusDto;
import com.example.tarkos.mappers.ApparatusMapper;
import com.example.tarkos.repositories.apparatuses.ApparatusRepository;
import com.example.tarkos.services.apparatuses.ApparatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class ApparatusServiceImpl implements ApparatusService {

    private final ApparatusRepository apparatusRepository;

    private final ApparatusMapper apparatusMapper;

    public ApparatusServiceImpl(
            ApparatusRepository apparatusRepository,
            ApparatusMapper apparatusMapper
    ) {
        this.apparatusRepository = apparatusRepository;
        this.apparatusMapper = apparatusMapper;
    }

    public Collection<ApparatusDto> getApparatus() {
        log.info("Getting apparatus");

        return apparatusRepository.getApparatuses()
                .stream()
                .map(apparatusMapper::convertToApparatusDto)
                .toList();
    }

    public Collection<String> getApparatusModsById(Integer id) {
        log.info("Getting apparatus mods by id - {}", id);

        return apparatusRepository.getApparatusModsById(id);
    }

    public Collection<String> getAllOperationsTypes() {
        log.info("Getting all operation's types");

        return apparatusRepository.getAllOperationsTypes();
    }
}
