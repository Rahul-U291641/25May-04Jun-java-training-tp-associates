package com.ust.inventory_consumer_service.service;

import com.ust.inventory_consumer_service.entity.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumerService {

    @KafkaListener(
            topics = "order-string-topic",
            groupId = "inventory-group",
            containerFactory = "stringKafkaListenerContainerFactory"
    )
    public void consumeOrderEvent(String message) {
        // Logic to process the received order event and update inventory
        System.out.println("Received order event & Inventory updated : " + message);
        // Here you can add code to update the inventory based on the order details
    }

    @KafkaListener(
            topics = "order-json-topic",
            groupId = "inventory-group",
            containerFactory = "jsonKafkaListenerContainerFactory"
    )
    public void consumeOrderEvent(OrderEvent order) {
        // Logic to process the received order event and update inventory
        System.out.println("Received order event & Inventory updated : {" + order.toString() + "}");
        // Here you can add code to update the inventory based on the order details
    }
}
