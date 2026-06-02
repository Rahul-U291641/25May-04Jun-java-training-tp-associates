package com.ust.notification_consumer_service.entity;

import lombok.Data;

@Data
public class OrderEvent {
    private Long id;
    private String productName;
    private double amount;
}
