package com.ust.bankingKafka.service;

import com.ust.bankingKafka.exception.RetryableException;
import com.ust.bankingKafka.model.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TransactionProcessingService {

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Value("${bank.kafka.topics.fraud}")
    private String fraudTopic;

    @Value("${bank.kafka.fraud.threshold}")
    private Double fraudThreshold;

    public void process(Transaction txn) {
        String txnId = txn.getTransactionId();

        // Permanent Failure - will NOT be retried (sent directly to DLT)
        if(txnId.contains("PERMANENT")) {
            throw new RuntimeException("Permanent Failure - No Retry");
        }

        // Transient/Retryable Failure - will be retried by ErrorHandler
        if(txnId.contains("TRANSIENT")) {
            log.warn("Transient failure for transaction: {}, will retry", txnId);
            // This will be retried up to 3 times by the error handler
            // After 3 attempts, it will be sent to DLT
            throw new RetryableException("Temporary Failure - Will Retry");
        }

        // Fraud Detection
        checkFraud(txn);

        log.info("Transaction Processed Successfully: {}", txnId);
    }

    private void checkFraud(Transaction txn) {
        if (txn.getAmount() > fraudThreshold) {

            // Publish fraud event
            kafkaTemplate.send(
                    fraudTopic,
                    txn.getTransactionId(),
                    txn);

            log.info("Fraud Alert Sent : {}", txn.getTransactionId());
        }
    }
}


