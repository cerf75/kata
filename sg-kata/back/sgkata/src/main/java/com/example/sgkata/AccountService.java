package com.example.sgkata;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountService {
    private static final String IBAN = "FRMyIban";

    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    AccountService() {
        createAccount(new BankAccount("Richard HART", IBAN, new BigDecimal("10000.00")));
    }

    public void createAccount(Account account) {
        this.accounts.put(account.getIban(), account);
    }

    public Account findByIban(String iban) {
        if (!accounts.containsKey(iban)) {
            throw new NoSuchElementException("Iban not found: " + iban);
        }
        return accounts.get(iban);
    }
}
