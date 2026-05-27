package com.assignment2.product_inventry.entity;

//@Entity
//@Table(name = "products")
public class Product {
    //Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private String category;
}
