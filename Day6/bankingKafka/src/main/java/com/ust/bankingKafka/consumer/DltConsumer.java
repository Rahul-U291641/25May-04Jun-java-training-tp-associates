package com.ust.bankingKafka.consumer;

import com.ust.bankingKafka.model.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DltConsumer {

    @KafkaListener(
            topics = "${bank.kafka.topics.dlt}",
            groupId = "${bank.kafka.groups.dlt}",
            containerFactory = "dltKafkaListenerContainerFactory"
    )
    public void consume(
            Transaction transaction,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {

        try {
            log.error("========== DLT MESSAGE ==========");
            log.error("Topic      : {}", topic);
            log.error("Partition  : {}", partition);
            log.error("Offset     : {}", offset);
            log.error("Txn Id     : {}", transaction.getTransactionId());
            log.error("Account No : {}", transaction.getAccountNumber());
            log.error("Amount     : {}", transaction.getAmount());
            log.error("Type       : {}", transaction.getType());
            log.error("=================================");
            
            // CRITICAL FIX: Acknowledge the message after successful processing
            // This commits the offset and removes the message from the consumer's view
            acknowledgment.acknowledge();
            log.info("DLT message acknowledged successfully");
        } catch (Exception e) {
            log.error("Error processing DLT message", e);
            // Do NOT acknowledge if there's an error - allow for manual retry
            // In real scenarios, you might want to log this to a database or send alerts
        }
    }
}

