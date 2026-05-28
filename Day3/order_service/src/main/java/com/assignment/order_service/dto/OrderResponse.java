package com.assignment.order_service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "orderId",
        "netQuantity",
        "totalPrice",
        "user",
        "product"})
public class OrderResponse {
    private String orderId;
    private UserResponse user;
    private ProductResponse product;
    private int netQuantity;
    private double totalPrice;
}
