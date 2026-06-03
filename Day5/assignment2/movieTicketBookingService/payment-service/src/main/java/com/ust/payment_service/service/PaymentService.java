package com.ust.payment_service.service;

import com.ust.payment_service.entity.Booking;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PaymentService {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topics.email-topic}")
    private String emailTopic;

    @KafkaListener(
            topics = "${topics.payment-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "jsonKafkaListenerContainerFactory")
    public void processPaymentRequest(Booking booking) {
        log.info("Received payment request for booking: {}", booking.toString());
        try {
            Thread.sleep(5000);
            log.info("Processing payment for request: {}", booking.toString());
            Thread.sleep(3000);
            log.info("Payment successful for request and sent Kafka -> to raise booking confirmation email: {}", booking.toString());
            kafkaTemplate.send(emailTopic, booking);
        } catch (InterruptedException e) {
            log.error("Error processing payment request: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
