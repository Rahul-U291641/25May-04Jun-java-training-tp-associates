package com.ust.order_service.controller;

import com.ust.order_service.dto.ApiResponse;
import com.ust.order_service.dto.OrderRequest;
import com.ust.order_service.dto.OrderResponse;
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
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        if (orderResponse != null) {
            return new ApiResponse<>(true, "Order created successfully!", orderResponse);
        } else {
            return new ApiResponse<>(false, "Failed to create order!", null);
        }
    }

}
