package com.ust.order_service.controller;

import com.ust.order_service.dto.ApiResponse;
import com.ust.order_service.dto.OrderRequest;
import com.ust.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ApiResponse<?> createOrder(@RequestBody OrderRequest orderRequest) {
        boolean isOrderCreated = orderService.createOrder(orderRequest);
        if (isOrderCreated) {
            return new ApiResponse<String>(true, "Order created successfully!", null);
        } else {
            return new ApiResponse<String>(false, "Failed to create order!", null);
        }
    }

}
