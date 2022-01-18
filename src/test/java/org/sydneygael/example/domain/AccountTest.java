package org.sydneygael.example.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void should_new_account_throws_execption_when_deposit_an_amount_of_negative_amount() {
        assertThatThrownBy(() -> {
            Account account = new Account(new Balance(BigDecimal.ZERO));
            account.deposit(new Amount(BigDecimal.valueOf(10).negate()));
        })
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_withdraw_10_balance_of_10_when_withdraw_an_amount_of_12() {
        Account account = new Account(new Balance(BigDecimal.valueOf(12)));
        account.withdrawal(new Amount(BigDecimal.TEN));
        assertThat(account.balance()).isEqualTo(Balance.of(BigDecimal.valueOf(2)));
    }

    @Test
    void should_not_be_able_to_withdrawal_when_the_balance_amount_is_less_than_the_withdrawal() {

        assertThatThrownBy(() -> {
            Account account = new Account(new Balance(BigDecimal.ONE));
            account.withdrawal(new Amount(BigDecimal.TEN));
        })
                .isInstanceOf(RuntimeException.class);
    }


}