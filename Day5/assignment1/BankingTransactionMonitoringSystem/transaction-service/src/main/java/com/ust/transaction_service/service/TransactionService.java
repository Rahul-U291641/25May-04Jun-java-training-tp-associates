package com.ust.transaction_service.service;

import com.ust.transaction_service.entity.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TransactionService {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topics.transaction-topic}")
    private String transactionTopic;

    public boolean createTransaction(Transaction transaction) {
        // Logic to save the transaction to the database (not implemented here)
        try {
            kafkaTemplate.send(transactionTopic, transaction);
            log.info("Transaction sent to Kafka: {}", transaction.toString());
        } catch (Exception e) {
            log.error("Error saving transaction: {}", e.getMessage());
            return false;
        }

        return true;
    }
}
