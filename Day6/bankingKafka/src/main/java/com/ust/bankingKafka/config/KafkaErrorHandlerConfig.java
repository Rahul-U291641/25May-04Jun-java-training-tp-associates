package com.ust.bankingKafka.config;

import com.ust.bankingKafka.exception.RetryableException;
import com.ust.bankingKafka.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
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

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                recoverer,
                backOff);

        // CRITICAL: Only retry on RetryableException
        // All other exceptions (including RuntimeException) will be sent directly to DLT
        errorHandler.addNotRetryableExceptions(
                RuntimeException.class  // Generic RuntimeException goes directly to DLT
        );
        // But explicitly make RetryableException retryable since it extends RuntimeException
        errorHandler.addRetryableExceptions(
                RetryableException.class  // This will be retried
        );
        
        log.info("DefaultErrorHandler configured: max attempts=3, retryable=RetryableException, non-retryable=RuntimeException");
        
        return errorHandler;
    }
}