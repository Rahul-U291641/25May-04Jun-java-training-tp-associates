package com.assignment.order_service.entity;

import lombok.Data;

//@Entity
//@Table(name = "orders")
@Data
public class Order {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String userId;
    private String productId;
    private int quantity;
    private double price;
}
