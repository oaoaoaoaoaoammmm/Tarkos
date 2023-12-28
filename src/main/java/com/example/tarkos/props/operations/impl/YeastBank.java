package com.example.tarkos.props.operations.impl;

import com.example.tarkos.props.operations.Operation;
import org.springframework.stereotype.Component;

@Component
public class YeastBank implements Operation {

    @Override
    public String getOperation() {
        return "YEAST_BANK";
    }

    @Override
    public Double getCoefficient() {
        return 2.0;
    }
}
