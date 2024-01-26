package com.example.tarkos.props.operations.impl;

import com.example.tarkos.props.operations.Operation;
import org.springframework.stereotype.Component;

@Component
public class WaterPreparation implements Operation {

    @Override
    public String getOperation() {
        return "WATER_PREPARATION";
    }

    @Override
    public Double getCoefficient() {
        return 1.0;
    }
}
