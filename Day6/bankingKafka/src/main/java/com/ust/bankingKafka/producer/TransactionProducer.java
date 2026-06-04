package com.ust.bankingKafka.producer;

import com.ust.bankingKafka.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionProducer {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    @Value("${bank.kafka.topics.transaction}")
    public String transactionTopicName;

    public void sendTransaction(Transaction transaction) {

        kafkaTemplate.send(
                transactionTopicName,
                transaction.getTransactionId(),
                transaction);

        log.info("Published Transaction {}", transaction.getTransactionId());
    }
}