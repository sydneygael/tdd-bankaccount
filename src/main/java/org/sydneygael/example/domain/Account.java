package org.sydneygael.example.domain;

public class Account {

    private Balance balance;

    public Account(Balance balance) {
        this.balance = balance;
    }

    public Balance balance() {
        return balance;
    }

    public void deposit(Amount amount) {
        balance = balance.add(amount);
    }

    public void withdrawal(Amount amount) {
        balance = balance.subtract(amount);
    }
}
