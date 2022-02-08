package com.example.sgkata;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class DepositIntegrationTest {

    private static final String IBAN = "FRMyIban";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void createAccount() {
        accountService.createAccount(new BankAccount("Richard HART", IBAN));
    }

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot SG Kata!")));
    }

    @Test
    public void shouldDepositWithoutError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/accounts/{iban}/deposit", IBAN)
                        .param("amount", "1000000.00")
                        .param("reference", "FDJ.fr")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Deposit accepted - provisional new balance: 1,000,000.00")));
    }
}