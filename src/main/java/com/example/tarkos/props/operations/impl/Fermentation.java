package com.example.tarkos.props.operations.impl;

import com.example.tarkos.props.operations.Operation;
import org.springframework.stereotype.Component;

@Component
public class Fermentation implements Operation {

    @Override
    public String getOperation() {
        return "FERMENTATION";
    }

    @Override
    public Double getCoefficient() {
        return 1.0;
    }
}
