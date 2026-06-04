package com.ust.bankingKafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration class for Kafka topics.
 * Defines the main topic for transactions and a Dead Letter Topic (DLT) for failed messages.
 */
@Configuration
public class KafkaTopicConfig {

    @Value("${bank.kafka.topics.transaction}")
    private String transactionsTopicName;

    @Value("${bank.kafka.topics.dlt}")
    private String dltTopicName;

    @Value("${bank.kafka.topics.fraud}")
    private String fraudTopicName;

    /**
     * Creates the main topic for transactions.
     * @return NewTopic instance for transactions topic
     */
    @Bean
    public NewTopic transactionTopic() {
        return TopicBuilder.name(transactionsTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    /**
     * Creates the Dead Letter Topic for failed messages.
     * @return NewTopic instance for DLT
     */
    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder.name(dltTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic fraudTopic() {
        return TopicBuilder.name(fraudTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
