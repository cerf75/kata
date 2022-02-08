package com.example.sgkata;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/accounts/{iban}")
public class BankAccountController {

    private static final DecimalFormat DF = new DecimalFormat("#,###.00");

    AccountService accountService;

    BankAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/deposit")
    public String deposit(@PathVariable String iban, @RequestParam String amount, @RequestParam String reference) {
        Account account = accountService.findByIban(iban);
        account.deposit(new BigDecimal(amount), LocalDate.now(), reference);
        return "Deposit accepted - provisional new balance: " +  DF.format(account.getBalance());
    }

    @PostMapping("/withdraw")
    public String withdraw(@PathVariable String iban, @RequestParam String amount, @RequestParam String reference) {
        Account account = accountService.findByIban(iban);
        try {
            account.withdraw(new BigDecimal(amount), LocalDate.now(), reference);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds", e);
        }
        return "New balance: " +  DF.format(account.getBalance());
    }

    @GetMapping("/statement")
    public void statement(@PathVariable String iban, HttpServletResponse response) throws IOException {
        Account account = accountService.findByIban(iban);
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition","attachment;filename=BankStatement.txt");
        ServletOutputStream out = response.getOutputStream();
        out.println(account.getStatement().toString());
        out.flush();
        out.close();
    }
}
