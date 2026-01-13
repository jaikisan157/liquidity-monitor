package com.examplecom.fintech.liquidity_monitor.service;

import com.examplecom.fintech.liquidity_monitor.model.Account;
import com.examplecom.fintech.liquidity_monitor.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String accountNumber, BigDecimal initialBalance, String currency) {
        Account account = new Account(accountNumber, initialBalance, currency);
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
