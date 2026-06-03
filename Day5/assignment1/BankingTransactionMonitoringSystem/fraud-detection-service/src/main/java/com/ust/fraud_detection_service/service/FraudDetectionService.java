package com.ust.fraud_detection_service.service;

import com.ust.fraud_detection_service.entity.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FraudDetectionService {
    @Value("${topics.transaction-topic}")
    private String transactionTopic;

    @Value("${spring.kafka.consumer.group-id}")
    private String fraudulentTransactionTopic;

    @Value("${topics.alert-topic}")
    private String alertTopic;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(
            topics = "${topics.transaction-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "jsonKafkaListenerContainerFactory"
    )
    public void isFraudulentTransaction(Transaction transaction) {
        // Simple fraud detection logic (for demonstration purposes)
        if (transaction.getAmount() > 50000) {
            log.warn("Fraudulent transaction detected and sent alert to kafka: " + transaction.toString());
            kafkaTemplate.send(alertTopic, "Fraudulent transaction detected: " + transaction.toString());
        } else {
            log.info("Transaction is not fraudulent: " + transaction.toString());
        }
    }
}
