package com.examplecom.fintech.liquidity_monitor;

import com.examplecom.fintech.liquidity_monitor.model.Payment;
import com.examplecom.fintech.liquidity_monitor.repository.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class LiquidityMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiquidityMonitorApplication.class, args);
    }
}
