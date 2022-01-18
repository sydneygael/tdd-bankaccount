package org.sydneygael.example.domain;

import infra.StatementPrinter;

import java.time.Clock;
import java.time.LocalDateTime;

public class Account {

    private Balance balance;
    private final Clock clock;
    private Statement statement;

    public Account(Balance balance) {
        this.balance = balance;
        this.clock = Clock.systemDefaultZone();
        this.statement = new Statement();
    }

    public Account(Balance balance, Clock clock) {
        this.balance = balance;
        this.clock=clock;
        this.statement = new Statement();
    }

    protected Account(Balance balance, Clock clock, Statement statement) {
        this.balance = balance;
        this.statement = statement;
        this.clock=clock;
    }

    public Balance balance() {
        return balance;
    }

    public void deposit(Amount amount) {
        balance = balance.add(amount);
        statement.add(new Operation(OperationType.DEPOSIT, amount, LocalDateTime.now(clock)), balance);
    }

    public void withdrawal(Amount amount) {
        Balance afterOperation = balance.subtract(amount);
        if (afterOperation.isNegative()) {
            throw new RuntimeException("Cannot do Operation because balance is not enough");
        }
        balance = afterOperation;
        statement.add(new Operation(OperationType.WITHDRAW, amount, LocalDateTime.now(clock)), afterOperation);
    }

    public void print(StatementPrinter statementPrinter) {
        statement.print(statementPrinter);
    }
}
