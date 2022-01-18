package org.sydneygael.example.domain;

import java.math.BigDecimal;

public record Balance(BigDecimal value) {

    public static Balance of(BigDecimal value) {
        return new Balance(value);
    }
}
