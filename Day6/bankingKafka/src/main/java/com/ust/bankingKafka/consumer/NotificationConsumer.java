package com.ust.bankingKafka.consumer;

import com.ust.bankingKafka.model.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class NotificationConsumer {
    @KafkaListener(
            topics = "${bank.kafka.topics.success}",
            groupId = "${bank.kafka.groups.notification}",
            containerFactory = "jsonKafkaListenerContainerFactory"
    )
    public void notify(Transaction txn) {
        log.info("Email sent : {}", txn.getTransactionId());
        log.info("SMS sent : {}", txn.getTransactionId());
    }
}
