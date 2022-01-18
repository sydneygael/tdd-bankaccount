package org.sydneygael.example.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

    @Test
    void should_create_account_with_zero_balance() {
        Account account = new Account(new Balance(BigDecimal.ZERO));
        assertThat(account.balance()).isEqualTo(Balance.of(BigDecimal.ZERO));
    }

    @Test
    void should_new_account_have_balance_of_10_when_deposit_an_amount_of_10() {
        Account account = new Account(new Balance(BigDecimal.ZERO));
        account.deposit(new Amount(BigDecimal.TEN));
        assertThat(account.balance()).isEqualTo(Balance.of(BigDecimal.TEN));
    }


}