package org.sydneygael.example.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AmountTest {

    @Test
    void should_add_ten_to_balance_with_zero_value(){
        Amount amount = new Amount(BigDecimal.ZERO);

        assertThat(BigDecimal.ZERO).isEqualTo(amount.value());
    }

    @Test
    void should_throw_illegal_argument_exception() {
        assertThatThrownBy(() -> {
            Amount amount = new Amount(BigDecimal.TEN.negate());
        }).isInstanceOf(IllegalArgumentException.class);
    }
}