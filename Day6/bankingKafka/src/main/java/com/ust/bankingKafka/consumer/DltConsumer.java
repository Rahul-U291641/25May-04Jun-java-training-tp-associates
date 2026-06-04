package com.ust.bankingKafka.consumer;

import com.ust.bankingKafka.model.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DltConsumer {

    @KafkaListener(
            topics = "${bank.kafka.topics.dlt}",
            groupId = "${bank.kafka.groups.dlt}",
            containerFactory = "jsonKafkaListenerContainerFactory"
    )
    public void consume(Transaction txn) {
        log.error("Received DLT Message : {}", txn.getTransactionId());
    }
}
