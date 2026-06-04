package com.ust.order_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {
    private Long userId;
    private String productName;
    private int quantity;
}
