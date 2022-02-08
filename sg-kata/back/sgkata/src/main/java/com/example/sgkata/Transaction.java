package com.example.sgkata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final static DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final LocalDate date;
    private final String Reference;
    private final BigDecimal amount;

    public Transaction(LocalDate date, String reference, BigDecimal amount) {
        this.date = date;
        Reference = reference;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getReference() {
        return Reference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s", this.getDate().format(DF), this.getReference(), this.getAmount());
    }
}
