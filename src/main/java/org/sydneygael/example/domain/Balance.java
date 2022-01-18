package org.sydneygael.example.domain;

import java.math.BigDecimal;

public record Balance(BigDecimal value) {

    public Balance add(Amount amount) {
        return new Balance(value.add(amount.value()));
    }

    public static Balance of(BigDecimal value) {
        return new Balance(value);
    }

    public Balance subtract(Amount amount) {
        return new Balance(value.subtract(amount.value()));
    }

    public boolean isNegative() {
        return value.signum() == -1;
    }
}
