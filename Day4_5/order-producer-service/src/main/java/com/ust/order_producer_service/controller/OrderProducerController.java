package com.ust.order_producer_service.controller;

import com.ust.order_producer_service.dto.ApiResponse;
import com.ust.order_producer_service.entity.OrderEvent;
import com.ust.order_producer_service.service.OrderProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderProducerController {

    @Autowired
    private OrderProducerService orderProducerService;

    @PostMapping("/{id}")
    public ApiResponse<Long> createOrder(@PathVariable Long id) {
        // Logic to create an order and send it to Kafka
        orderProducerService.sendOrderToKafka(id);
        return new ApiResponse<>("success", "Order created and sent to Kafka!", id);
    }

    @PostMapping
    public ApiResponse<OrderEvent> createOrder(@RequestBody OrderEvent order) {
        // Logic to create an order without an ID and send it to Kafka
        orderProducerService.sendOrderToKafka(order);
        return new ApiResponse<>("success", "Order created without ID and sent to Kafka!", order);
    }

    @PostMapping("/with-status")
    public ApiResponse<OrderEvent> createOrderWithStatus(@RequestBody OrderEvent order) {
        // Logic to create an order with a status and send it to Kafka
        orderProducerService.sendOrderToKafkaWithStatus(order);
        return new ApiResponse<>("success", "Order created with status and sent to Kafka!", order);
    }

}
