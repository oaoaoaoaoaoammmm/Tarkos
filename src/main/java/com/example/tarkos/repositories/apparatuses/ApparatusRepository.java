package com.example.tarkos.repositories.apparatuses;

import com.example.tarkos.models.Apparatus;
import com.example.tarkos.models.Task;

import java.util.Collection;
import java.util.List;

public interface ApparatusRepository {
    Collection<Apparatus> getApparatuses();
    Collection<String> getApparatusModsById(Integer id);
    Collection<String> getAllOperationsTypes();
    void addApparatusesTasks(List<Task> tasks);
}
