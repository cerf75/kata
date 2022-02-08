package com.example.sgkata;

import java.util.List;

public class BankStatement implements Statement {
    private static final String LSEP = System.getProperty("line.separator");

    private final Account account;

    public BankStatement(Account account) {
        this.account = account;
    }

    @Override
    public String getHeader() {
        return String.format("Account name: %s, IBAN: %s", account.getName(), account.getIban());
    }

    @Override
    public List<String> getItems() {
        return account.getStatementItems();
    }

    @Override
    public String getFooter() {
        return String.format("New balance: %s", account.getBalance());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader());
        sb.append(LSEP);
        getItems().forEach(item -> {
            sb.append(item);
            sb.append(LSEP);
        });
        sb.append(getFooter());
        return sb.toString();
    }
}