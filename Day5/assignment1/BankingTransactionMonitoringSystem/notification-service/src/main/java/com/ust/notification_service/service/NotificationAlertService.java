package com.ust.notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationAlertService {
    @KafkaListener(
            topics = "${topics.alert-topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeTransactionAlertEvent(String message) {
        // Logic to process the received transaction alert event and send a notification
        System.out.println("** Received transaction alert event & Notification sent : " + message);
        // Here you can add code to send a notification, e.g., via email or SMS
    }
}
