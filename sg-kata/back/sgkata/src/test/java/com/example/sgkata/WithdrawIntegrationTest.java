package com.example.sgkata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WithdrawIntegrationTest {

    private static final String IBAN = "FRMyIban";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void createAccount() {
        accountService.createAccount(new BankAccount("Richard HART", IBAN, new BigDecimal("1000000.00")));
     }

    @Test
    public void shouldWithdrawWithoutError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/accounts/{iban}/withdraw", IBAN)
                        .param("amount", "400000.00")
                        .param("reference", "Pension Alimentaire")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("New balance: 600,000.00")));
    }

    @Test
    public void shouldRefuseOverdrawal() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/accounts/{iban}/withdraw", IBAN)
                        .param("amount", "1000001.00")
                        .param("reference", "Beer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(status().reason(equalTo("Insufficient funds")));
    }
}