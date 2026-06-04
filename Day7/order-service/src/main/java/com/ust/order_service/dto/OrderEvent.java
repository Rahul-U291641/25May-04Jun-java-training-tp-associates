package com.ust.order_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderEvent {
    private Long orderId;
    private Long userId;
    private String productName;
    private int quantity;
    private String status;
}
