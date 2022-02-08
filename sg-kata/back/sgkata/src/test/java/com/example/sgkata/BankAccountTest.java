package com.example.sgkata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {
    private static final String MY_IBAN = "FR1234567890";
    private static final String MY_NAME = "Richard HART";

    private Account myAccount;

    @BeforeEach
    void createMyAccount() {
        myAccount = new BankAccount(MY_NAME, MY_IBAN);
   }

    @Test
    void shouldBeAbleToCreateAccount() {
        assertEquals(MY_NAME, myAccount.getName(), "wrong account name");
        assertEquals(MY_IBAN, myAccount.getIban(), "wrong IBAN");
    }

    @Test
    void depositShouldIncreaseBalance() {
        myAccount.deposit(new BigDecimal("1000.00"), LocalDate.of(2022, 1, 2), "");
        assertEquals(new BigDecimal("1000.00"), myAccount.getBalance());
    }

    @Test
    void shouldNotAllowOverDrawAccount() {
        myAccount.deposit(new BigDecimal("1000.00"), LocalDate.of(2022, 1, 2), "");
        assertThrows(IllegalStateException.class, () -> myAccount.withdraw(new BigDecimal("1000.01"), LocalDate.of(2022, 2, 5), ""));
    }

    @Test
    void withdrawalShouldDecreaseBalance() {
        myAccount.deposit(new BigDecimal("1000.00"), LocalDate.of(2022, 1, 2), "");
        myAccount.withdraw(new BigDecimal("500.01"), LocalDate.of(2022, 2, 5), "");
        assertEquals(new BigDecimal("499.99"), myAccount.getBalance());
    }


}
