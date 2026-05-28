package com.assignment.order_service.controller;

import com.assignment.order_service.dto.ApiResponse;
import com.assignment.order_service.dto.OrderResponse;
import com.assignment.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderServiceController {
    @Autowired
    OrderService orderService;

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable String id) {
      return orderService.getOrderById(id);
    }
}
