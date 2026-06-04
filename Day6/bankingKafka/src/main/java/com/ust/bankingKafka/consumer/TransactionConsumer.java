package com.ust.bankingKafka.consumer;

import com.ust.bankingKafka.model.Transaction;
import com.ust.bankingKafka.service.TransactionProcessingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class TransactionConsumer {

    @Autowired
    private TransactionProcessingService service;

    @KafkaListener(
            topics = "${bank.kafka.topics.transaction}",
            groupId = "${bank.kafka.groups.banking}",
            containerFactory = "jsonKafkaListenerContainerFactory"
    )
    public void consume(Transaction txn, Acknowledgment acknowledgment) {
        String txnId = txn.getTransactionId();
        
        log.info("Processing transaction: {}", txnId);
        
        // Process the transaction
        // If exception is thrown, it will be caught by DefaultErrorHandler
        // Exceptions will NOT trigger acknowledgment (offset stays unchanged)
        // Error handler will then retry according to configuration
        service.process(txn);
        
        // Only executed if service.process() succeeds
        // Manual acknowledgment commits the offset
        acknowledgment.acknowledge();
        log.info("Successfully consumed transaction: {}", txnId);
    }
}
