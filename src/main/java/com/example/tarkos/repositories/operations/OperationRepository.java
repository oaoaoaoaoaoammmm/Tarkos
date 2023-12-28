package com.example.tarkos.repositories.operations;

public interface OperationRepository {
    void insertApparatusOperationByName(String apparatusName);
    void insertModInLastOperationByName(String modeName);
    void insertApparatusProductsInLastOperation(String productName, Double count);
}
