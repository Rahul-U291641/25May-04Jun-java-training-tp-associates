package com.ust.order_producer_service.service;

import com.ust.order_producer_service.entity.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducerService {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderToKafka(Long id) {
        // Logic to create an order and send it to Kafka
        String orderMessage = "Order with ID: " + id + " has been created.";
        kafkaTemplate.send(" order-string-topic", orderMessage);
    }

    public void sendOrderToKafka(OrderEvent order) {
        // Logic to create an order and send it to Kafka
        kafkaTemplate.send("order-json-topic", order);
    }
}
