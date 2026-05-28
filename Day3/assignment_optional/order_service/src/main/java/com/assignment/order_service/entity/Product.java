package com.assignment.order_service.entity;

import lombok.Data;

//@Entity
//@Table(name = "products")
@Data
public class Product {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private double price;
}
