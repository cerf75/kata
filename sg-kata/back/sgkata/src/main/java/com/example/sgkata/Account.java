package com.example.sgkata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface Account {
    String getName();
    String getIban();
    void deposit(BigDecimal amount, LocalDate of, String reference);
    void withdraw(BigDecimal amount, LocalDate of, String reference);
    BigDecimal getBalance();
    Statement getStatement();
    List<String> getStatementItems();
}
