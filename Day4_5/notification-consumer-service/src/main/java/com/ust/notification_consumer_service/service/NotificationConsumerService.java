package com.ust.notification_consumer_service.service;

import com.ust.notification_consumer_service.entity.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumerService {

    @KafkaListener(
            topics = "order-string-topic",
            groupId = "notification-group",
            containerFactory = "stringKafkaListenerContainerFactory"
    )
    public void consumeOrderEvent(String message) {
        // Logic to process the received order event and send a notification
        System.out.println("Received order event & Notification sent : " + message);
        // Here you can add code to send a notification, e.g., via email or SMS
    }

    @KafkaListener(
            topics = "order-json-topic",
            groupId = "notification-group",
            containerFactory = "jsonKafkaListenerContainerFactory"
    )
    public void consumeOrderJsonEvent(OrderEvent order) {
        // Logic to process the received order event and send a notification
        System.out.println("Received order json event & Notification sent : {" + order.toString()+ "}");
        // Here you can add code to send a notification, e.g., via email or SMS
    }

    @KafkaListener(
            topics = "order-status-topic",
            groupId = "notification-group",
            containerFactory = "stringKafkaListenerContainerFactory"
    )
    public void consumeOrderStatusEvent(String status) {
        // Logic to process the received order status event and send a notification
        if (status.equalsIgnoreCase("\"ORDER_DELIVERED\"")) {
            System.out.println("Received DELIVERED status event & Notification sent : " + status);
            // Here you can add code to send a notification, e.g., via email or SMS
        } else {
            System.out.println("Order still is in process: " + status);
        }
    }
}
