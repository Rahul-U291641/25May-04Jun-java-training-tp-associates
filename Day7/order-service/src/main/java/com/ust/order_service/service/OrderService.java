package com.ust.order_service.service;

import com.ust.order_service.dto.OrderEvent;
import com.ust.order_service.dto.OrderRequest;
import com.ust.order_service.modal.Order;
import com.ust.order_service.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Value("${order.kafka.topics.order}")
    private String ORDER_TOPIC;

    @Autowired
    KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public boolean createOrder(OrderRequest orderRequest) {
        Order order = null;
        try {
            order = orderRepository.save(Order.builder()
                    .productName(orderRequest.getProductName())
                    .userId(orderRequest.getUserId())
                    .quantity(orderRequest.getQuantity())
                    .build());
            log.info("Order created successfully and Order ID : {} ", order.getOrderId());
        } catch (Exception e) {
            log.error("** Failed to create a order for product : {}", orderRequest.getProductName());
            throw new RuntimeException(e);
        }

        OrderEvent event = OrderEvent.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .productName(order.getProductName())
                .quantity(order.getQuantity())
                .status("CREATED")
                .build();

        publishOrderCreatedEvent(event);

        log.info("Publish order {} \"CREATED\" event to Kafka", order.getOrderId());
        return true;
    }

    private void publishOrderCreatedEvent(OrderEvent event) {
        log.info("Received a order event to publish!");
        try {
            kafkaTemplate.send(ORDER_TOPIC, event);
        } catch (Exception e) {
            log.info("Failed to send order event to kafka topic : {}", ORDER_TOPIC);
            throw new RuntimeException(e);
        }
    }
}
