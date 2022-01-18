package org.sydneygael.example.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record Amount (BigDecimal value){

    public Amount{
        Objects.requireNonNull(value);
        if(value.signum()==-1)
            throw new IllegalArgumentException("Amount must not be negative");
    }
}
