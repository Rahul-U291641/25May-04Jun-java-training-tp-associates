package com.ust.bankingKafka.consumer;

import com.ust.bankingKafka.model.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class FraudConsumer {

    @KafkaListener(
            topics = "${bank.kafka.topics.fraud}",
            groupId = "${bank.kafka.groups.fraud}",
            containerFactory = "jsonKafkaListenerContainerFactory"
    )
    public void consume(Transaction transaction) {
        log.warn(
                "FRAUD ALERT - TransactionId: {}, Account: {}, Amount: {}",
                transaction.getTransactionId(),
                transaction.getAccountNumber(),
                transaction.getAmount());
    }
}