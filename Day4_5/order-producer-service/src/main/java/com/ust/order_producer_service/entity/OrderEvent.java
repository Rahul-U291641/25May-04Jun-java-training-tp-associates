package com.ust.order_producer_service.entity;

import lombok.Data;

//@Entity
//@Table(name = "orders")
@Data
public class OrderEvent {
    private Long id;
    private String productName;
    private double amount;
}
