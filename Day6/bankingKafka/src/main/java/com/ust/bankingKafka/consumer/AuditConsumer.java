package com.ust.bankingKafka.consumer;

import com.ust.bankingKafka.model.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AuditConsumer {

    @KafkaListener(
            topics = "${bank.kafka.topics.transaction}",
            groupId = "${bank.kafka.groups.audit}",
            containerFactory = "jsonKafkaListenerContainerFactory"
    )
    public void audit(Transaction txn) {
        log.info("Auditing transaction: {}", txn.getTransactionId());
    }
}
