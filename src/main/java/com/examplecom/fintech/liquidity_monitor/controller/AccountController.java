package com.examplecom.fintech.liquidity_monitor.controller;

import com.examplecom.fintech.liquidity_monitor.model.Account;
import com.examplecom.fintech.liquidity_monitor.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Create account
    @PostMapping
    public Account createAccount(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal balance,
            @RequestParam String currency) {
        return accountService.createAccount(accountNumber, balance, currency);
    }

    // Get all accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Get one account
    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }

    // Deposit money
    @PostMapping("/{accountNumber}/deposit")
    public Account deposit(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount) {
        return accountService.deposit(accountNumber, amount);
    }

    // Withdraw money
    @PostMapping("/{accountNumber}/withdraw")
    public Account withdraw(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount) {
        return accountService.withdraw(accountNumber, amount);
    }
}
