package com.ust.bankingKafka.config;

import com.ust.bankingKafka.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
@RequiredArgsConstructor
public class KafkaErrorHandlerConfig {

    @Value("${bank.kafka.topics.dlt}")
    private String dltTopic;

    @Value("${bank.kafka.retry.initial-interval}")
    private long initialInterval;

    @Value("${bank.kafka.retry.multiplier}")
    private double multiplier;

    @Value("${bank.kafka.retry.max-interval}")
    private long maxInterval;

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Transaction> kafkaTemplate) {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        kafkaTemplate,
                        (record, ex) -> new TopicPartition(
                                dltTopic,
                                record.partition()));

        ExponentialBackOff backOff = new ExponentialBackOff();

        backOff.setInitialInterval(initialInterval);
        backOff.setMultiplier(multiplier);
        backOff.setMaxInterval(maxInterval);

        return new DefaultErrorHandler(
                recoverer,
                backOff);
    }
}