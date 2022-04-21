package com.example.paymentsimulation.model;

import jdk.jfr.DataAmount;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String name;
    private String surName;
    private String email;
    private String address;

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
    private List<Payment> paymentList;
}
