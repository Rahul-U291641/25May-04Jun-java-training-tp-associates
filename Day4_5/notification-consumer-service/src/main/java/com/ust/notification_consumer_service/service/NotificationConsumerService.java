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
}
