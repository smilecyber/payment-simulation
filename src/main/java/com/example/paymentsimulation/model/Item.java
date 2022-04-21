package com.example.paymentsimulation.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String code;

    @ManyToMany(mappedBy = "items")
    private List<Cart> carts;
}
