package com.example.paymentsimulation.repository;

import com.example.paymentsimulation.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
