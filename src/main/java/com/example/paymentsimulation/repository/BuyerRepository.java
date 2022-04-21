package com.example.paymentsimulation.repository;

import com.example.paymentsimulation.model.Buyer;
import com.example.paymentsimulation.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}
