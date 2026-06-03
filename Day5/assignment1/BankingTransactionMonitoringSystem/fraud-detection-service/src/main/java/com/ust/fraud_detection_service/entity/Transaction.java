package com.ust.fraud_detection_service.entity;

import lombok.Data;

//@Entity
// @Table(name = "transactions")
@Data
public class Transaction {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String description;
}
