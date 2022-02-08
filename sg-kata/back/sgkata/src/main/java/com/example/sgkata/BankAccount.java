package com.example.sgkata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BankAccount implements Account {
    private final String name;
    private final String iban;
    private BigDecimal balance = new BigDecimal("0");
    private final Collection<Transaction> transactions = new ConcurrentLinkedQueue<>();

    BankAccount(String name, String iban) {
        this.name = name;
        this.iban = iban;
    }

    BankAccount(String name, String iban, BigDecimal initialBalance) {
        this.name = name;
        this.iban = iban;
        this.balance = initialBalance;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getIban() {
        return this.iban;
    }

    @Override
    public void deposit(BigDecimal amount, LocalDate date, String reference) {
        this.balance = this.balance.add(amount);
        this.transactions.add(new Transaction(date, reference, amount));
    }

    @Override
    public void withdraw(BigDecimal amount, LocalDate date, String reference) {
        if (amount.compareTo(this.balance) > 0) {
            throw new IllegalStateException("Balance too low to withdraw: " + amount);
        }

        this.balance = this.balance.subtract(amount);
        this.transactions.add(new Transaction(date, reference, amount.negate()));
    }

    @Override
    public BigDecimal getBalance() {
        return this.balance;
    }

    @Override
    public Statement getStatement() {
        return new BankStatement(this);
    }

    @Override
    public List<String> getStatementItems() {
        List<String> statementItems = new ArrayList<>();
        for (Transaction transaction : transactions) {
            statementItems.add(transaction.toString());
        }
        return statementItems;
    }
}
