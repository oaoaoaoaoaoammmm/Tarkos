package com.example.tarkos.services.operations;

import org.springframework.data.util.Pair;

import java.util.Collection;

public interface OperationService {
    void makeBeer(String operationType, String apparatusName, String modeName, Collection<Pair<String, Double>> pairs);
    void register(String operation, Double coefficient);
}
