package com.example.sgkata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceTest {
    AccountService accountService = new AccountService();

    private static final String MY_IBAN = "FR1234567890";
    private static final String MY_NAME = "Richard HART";

    @BeforeEach
    void createMyAccount() {
        this.accountService.createAccount(new BankAccount(MY_NAME, MY_IBAN));
    }

    @Test
    void shouldFetchMyAccount() {
        Account myAccount = accountService.findByIban(MY_IBAN);
        assertEquals(MY_NAME, myAccount.getName(), "wrong account name");
    }

}
