package com.example.sgkata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BankStatementTest {
    private static final String MY_IBAN = "FR1234567890";
    private static final String MY_NAME = "Richard HART";

    private Account myAccount;

    @BeforeEach
    void createMyAccount() {
        myAccount = new BankAccount(MY_NAME, MY_IBAN);
   }

    @Test
    void shouldReturnEmptyStatement() {
        assertTrue(myAccount.getStatement().getHeader().contains(MY_NAME), "No name in statement header");
        assertTrue(myAccount.getStatement().getHeader().contains(MY_IBAN), "No IBAN in statement header");
        assertEquals(0, myAccount.getStatement().getItems().size(), "Items not empty");
    }

    @Test
    void shouldReturnStatementWithOneTransaction() {
        myAccount.deposit(new BigDecimal("100.00"), LocalDate.of(2022, 1, 2), "Deposit 100.00");
        assertEquals(1, myAccount.getStatement().getItems().size() , "Items size");
    }

    @Test
    void shouldReturnStatementWithTwoTransactions() {
        myAccount.deposit(new BigDecimal("200.00"), LocalDate.of(2022, 1, 2), "Deposit 200.00");
        myAccount.withdraw(new BigDecimal("150.00"), LocalDate.of(2022, 2, 5), "Withdraw 150.00");
        assertEquals(2, myAccount.getStatement().getItems().size() , "Items size");
    }

    @Test
    void shouldShowDateCorrectlyOnStatement() {
        myAccount.deposit(new BigDecimal("200.00"), LocalDate.of(2022, 2, 5), "Deposit 200.00");
        myAccount.withdraw(new BigDecimal("150.00"), LocalDate.of(2022, 2, 6), "Withdraw 150.00");
        assertTrue(myAccount.getStatement().getItems().get(0).contains("05-02-2022"),"Bad date: " + myAccount.getStatement().getItems().get(0));
        assertTrue(myAccount.getStatement().getItems().get(1).contains("06-02-2022"),"Bad date: " + myAccount.getStatement().getItems().get(1));
    }

    @Test
    void shouldShowReferenceCorrectlyOnStatement() {
        myAccount.deposit(new BigDecimal("200.00"), LocalDate.of(2022, 2, 5), "Deposit ref");
        myAccount.withdraw(new BigDecimal("150.00"), LocalDate.of(2022, 2, 6), "Withdraw ref");
        assertTrue(myAccount.getStatement().getItems().get(0).contains("Deposit ref"),"Bad reference: " + myAccount.getStatement().getItems().get(0));
        assertTrue(myAccount.getStatement().getItems().get(1).contains("Withdraw ref"),"Bad reference: " + myAccount.getStatement().getItems().get(1));
    }

    @Test
    void shouldShowAmountsCorrectlyOnStatement() {
        myAccount.deposit(new BigDecimal("200.00"), LocalDate.of(2022, 2, 5), "Deposit ref");
        myAccount.withdraw(new BigDecimal("150.00"), LocalDate.of(2022, 2, 6), "Withdraw ref");
        assertTrue(myAccount.getStatement().getItems().get(0).contains("\t200.00"),"Bad amount: " + myAccount.getStatement().getItems().get(0));
        assertTrue(myAccount.getStatement().getItems().get(1).contains("-150.00"),"Bad amount: " + myAccount.getStatement().getItems().get(1));
    }

    @Test
    void shouldShowNewBalanceCorrectlyOnStatement() {
        myAccount.deposit(new BigDecimal("200.00"), LocalDate.of(2022, 2, 5), "Deposit ref");
        myAccount.withdraw(new BigDecimal("150.00"), LocalDate.of(2022, 2, 6), "Withdraw ref");
        assertTrue(myAccount.getStatement().getFooter().contains("New balance: 50.00"),"Bad balance: " + myAccount.getStatement().getItems().get(1));
    }

}
