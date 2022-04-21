package com.example.paymentsimulation.repository;

import com.example.paymentsimulation.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
