package com.ust.order_service.service;

import com.ust.order_service.dto.OrderEvent;
import com.ust.order_service.dto.OrderRequest;
import com.ust.order_service.dto.OrderResponse;
import com.ust.order_service.modal.Order;
import com.ust.order_service.producer.OrderProducer;
import com.ust.order_service.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProducer orderProducer;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = null;
        try {
            order = orderRepository.save(Order.builder()
                    .productName(orderRequest.getProductName())
                    .userId(orderRequest.getUserId())
                    .quantity(orderRequest.getQuantity())
                    .build());

            log.info("Order created successfully and Order ID : {} ", order.getOrderId());
            OrderEvent event = OrderEvent.builder()
                    .orderId(order.getOrderId())
                    .userId(order.getUserId())
                    .productName(order.getProductName())
                    .quantity(order.getQuantity())
                    .status("CREATED")
                    .build();

            orderProducer.publishOrderCreatedEvent(event);

            log.info("Publish order {} \"CREATED\" event to Kafka", order.getOrderId());
            return OrderResponse.builder()
                    .orderId(event.getOrderId())
                    .status(event.getStatus())
                    .build();
        } catch (Exception e) {
            log.error("** Failed to create a order for product : {} : {} ", orderRequest.getProductName(), e);
            throw new RuntimeException(e);
        }
    }
}
