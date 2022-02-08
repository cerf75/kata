package com.example.sgkata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StatementIntegrationTest {

    private static final String IBAN = "FRMyIban";
    private static final String TODAY = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void createAccount() {
        accountService.createAccount(new BankAccount("Richard HART", IBAN, new BigDecimal("10000.00")));
    }

    @Test
    public void shouldProduceCorrectStatement() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/accounts/{iban}/deposit", IBAN)
                .param("amount", "3000.00")
                .param("reference", "FDJ.fr")
                .accept(MediaType.APPLICATION_JSON));

        mvc.perform(MockMvcRequestBuilders.post("/accounts/{iban}/withdraw", IBAN)
                .param("amount", "4000.00")
                .param("reference", "Pension Alimentaire")
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/accounts/{iban}/statement", IBAN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Scanner scanner = new Scanner(content);
        assertEquals("Account name: Richard HART, IBAN: FRMyIban", scanner.nextLine(), "Bad header");
        assertEquals(TODAY + "\tFDJ.fr\t3000.00", scanner.nextLine(), "Bad header");
        assertEquals(TODAY + "\tPension Alimentaire\t-4000.00", scanner.nextLine(), "Bad header");
        assertEquals("New balance: 9000.00", scanner.nextLine(), "Bad header");
        scanner.close();
    }

}