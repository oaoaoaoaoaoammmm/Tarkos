package com.example.tarkos.props.operations.impl;

import com.example.tarkos.props.operations.Operation;
import org.springframework.stereotype.Component;

@Component
public class Cooking implements Operation {

    @Override
    public String getOperation() {
        return "COOKING";
    }

    @Override
    public Double getCoefficient() {
        return 0.9;
    }
}
