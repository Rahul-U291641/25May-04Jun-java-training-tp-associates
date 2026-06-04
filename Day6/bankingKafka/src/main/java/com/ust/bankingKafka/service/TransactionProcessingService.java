package com.ust.bankingKafka.service;

import com.ust.bankingKafka.exception.RetryableException;
import com.ust.bankingKafka.model.Transaction;
import com.ust.bankingKafka.rerty.ManualRetryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j2
public class TransactionProcessingService {

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Autowired
    private ManualRetryHandler retryHandler;

    @Value("${bank.kafka.topics.fraud}")
    private String fraudTopic;

    @Value("${bank.kafka.fraud.threshold}")
    private Double fraudThreshold;

    public void process(Transaction txn) {
        String txnId = txn.getTransactionId();

        // Permanent Failure
        if(txnId.contains("PERMANENT")) {
            throw new RuntimeException("Permanent Failure");
        }

        // Transient Failure
        if(txnId.contains("TRANSIENT")) {
            int attempt = retryHandler.incrementRetryCount(txnId);
            if(attempt < 3) {
                log.info("Attempt {} for transaction {}", attempt, txnId);
                throw new RetryableException("Temporary Failure");
            }
            retryHandler.clearRetry(txnId);
            log.info("Attempt 3 Success");
        }

        // Fraud Detection
        checkFraud(txn);

        log.info("Transaction Processed Successfully");
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
