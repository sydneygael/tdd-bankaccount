package org.sydneygael.example.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BalanceTest {

    @Test
    void should_add_ten_to_balance_with_zero_value(){
        Balance balance = new Balance(BigDecimal.ZERO);

        Balance newBalance = balance.add(new Amount(BigDecimal.TEN));

        assertThat(BigDecimal.TEN).isEqualTo(newBalance.value());
    }

    @Test
    void should_return_balance_of_ten_value(){
        Balance balance = Balance.of(BigDecimal.TEN);

        assertThat(balance).isEqualTo(new Balance(BigDecimal.TEN));
    }

}