package com.example.tarkos.props.operations;


import com.example.tarkos.services.operations.OperationService;
import org.springframework.beans.factory.annotation.Autowired;

public interface Operation {
    String getOperation();
    Double getCoefficient();

    @Autowired
    default void registerMySelf(OperationService operationService) {
        operationService.register(getOperation(), getCoefficient());
    }
}
