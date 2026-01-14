package com.examplecom.fintech.liquidity_monitor.service;

import com.examplecom.fintech.liquidity_monitor.model.Account;
import com.examplecom.fintech.liquidity_monitor.model.Payment;
import com.examplecom.fintech.liquidity_monitor.model.PaymentStatus;
import com.examplecom.fintech.liquidity_monitor.repository.AccountRepository;
import com.examplecom.fintech.liquidity_monitor.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Payment makePayment(
            String fromAccount,
            String toAccount,
            BigDecimal amount,
            String idempotencyKey
    ) {
        // 🔐 Idempotency check
        return paymentRepository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> processPayment(fromAccount, toAccount, amount, idempotencyKey));
    }

    private Payment processPayment(
            String fromAccount,
            String toAccount,
            BigDecimal amount,
            String idempotencyKey
    ) {
        Account sender = accountRepository.findById(fromAccount)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account receiver = accountRepository.findById(toAccount)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // 💰 Debit / Credit
        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        // 🧾 Persist accounts
        accountRepository.save(sender);
        accountRepository.save(receiver);

        // 📌 Persist payment
        Payment payment = new Payment(fromAccount, toAccount, amount, idempotencyKey);
        payment.setStatus(PaymentStatus.COMPLETED);

        return paymentRepository.save(payment);
    }
}
