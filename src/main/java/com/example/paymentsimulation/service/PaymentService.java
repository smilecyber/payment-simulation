package com.example.paymentsimulation.service;

import java.math.BigDecimal;

public interface PaymentService {
    void makePayment(Long merchantId, Long buyerId, BigDecimal amount);
}
