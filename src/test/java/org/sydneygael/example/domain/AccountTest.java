package org.sydneygael.example.domain;

import infra.ConsoleConsumerPrinter;
import infra.StatementPrinter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountTest {

    @Spy
    private Statement statement;

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

    @Test
    void should_deposit_20_and_withdrawal_10_from_account_with_0_balance(){
        Account account = new Account(new Balance(BigDecimal.ZERO));

        account.deposit(new Amount(new BigDecimal(20)));
        account.withdrawal(new Amount(BigDecimal.TEN));

        assertThat(new Balance(BigDecimal.TEN)).isEqualTo(account.balance());
    }

    @Test
    void should_add_operations_to_account_statements() {

        //prepare
        String instantExpected = "2022-01-18T14:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        Account account = new Account(new Balance(BigDecimal.ZERO),clock,statement);
        BigDecimal depositValue = BigDecimal.valueOf(100);
        Amount depositAmount = new Amount(depositValue);
        BigDecimal withdrawValue = BigDecimal.valueOf(20);
        Amount withdrawAmount = new Amount(withdrawValue);
        BigDecimal balanceValue = depositValue.add(withdrawValue.negate());

        //act

        account.deposit(depositAmount);
        account.withdrawal(withdrawAmount);

        //assert
        verify(statement,times(1)).add(new Operation(OperationType.DEPOSIT,depositAmount, LocalDateTime.now(clock)),Balance.of(depositValue));
        verify(statement,times(1)).add(new Operation(OperationType.WITHDRAW,withdrawAmount, LocalDateTime.now(clock)),Balance.of(balanceValue));
    }

    @Test
    void shloud_print_all_satements() {
        //prepare
        String instantExpected = "2022-01-18T16:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        Account account = new Account(new Balance(BigDecimal.ZERO),clock,statement);
        BigDecimal depositValue = BigDecimal.valueOf(100);
        Amount depositAmount = new Amount(depositValue);
        BigDecimal withdrawValue = BigDecimal.valueOf(20);
        Amount withdrawAmount = new Amount(withdrawValue);
        BigDecimal balanceValue = depositValue.add(withdrawValue.negate());

        StatementPrinter statementPrinter = new FakeStatementPrinter();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //act
        account.deposit(depositAmount);
        account.withdrawal(withdrawAmount);
        account.print(statementPrinter);

        assertThat("""
                | OPERATION | DATE | AMOUNT | BALANCE |
                | DEPOSIT | 2022-01-18 16:15 | 100 | 100 |
                | WITHDRAW | 2022-01-18 16:15 | 20 | 80 |
                """).isEqualTo(outContent.toString());
    }

    private static class FakeStatementPrinter implements StatementPrinter {

        @Override
        public void print(Statement statement) {
            new ConsoleConsumerPrinter().accept(statement);
        }
    }


}