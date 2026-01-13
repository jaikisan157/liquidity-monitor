package com.examplecom.fintech.liquidity_monitor.service;

import com.examplecom.fintech.liquidity_monitor.model.Account;
import com.examplecom.fintech.liquidity_monitor.repository.AccountRepository;
import jakarta.transaction.Transactional; // <-- Make sure this import is here
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create account
    public Account createAccount(String accountNumber, BigDecimal balance, String currency) {
        if (accountRepository.existsById(accountNumber)) {
            throw new RuntimeException("Account already exists");
        }
        Account account = new Account(accountNumber, balance, currency);
        return accountRepository.save(account);
    }

    // Get all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Get account by number
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Deposit money
    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    // Withdraw money
    @Transactional
    public Account withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }
}
