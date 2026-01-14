package com.examplecom.fintech.liquidity_monitor.controller;

import com.examplecom.fintech.liquidity_monitor.model.Payment;
import com.examplecom.fintech.liquidity_monitor.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment makePayment(
            @RequestParam String fromAccount,
            @RequestParam String toAccount,
            @RequestParam BigDecimal amount,
            @RequestParam String idempotencyKey
    ) {
        return paymentService.makePayment(
                fromAccount,
                toAccount,
                amount,
                idempotencyKey
        );
    }
}
