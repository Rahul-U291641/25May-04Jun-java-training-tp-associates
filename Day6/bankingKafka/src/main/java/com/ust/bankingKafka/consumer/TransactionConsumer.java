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
        // Process the transaction
        service.process(txn);
        // Manually acknowledge the message after processing
        acknowledgment.acknowledge();
        // Log the consumed transaction
        log.info("Consumed transaction: {}", txn.getTransactionId());
    }
}
