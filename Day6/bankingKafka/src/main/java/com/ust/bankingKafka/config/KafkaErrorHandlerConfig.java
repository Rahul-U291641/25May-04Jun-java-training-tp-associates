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
import org.springframework.util.backoff.FixedBackOff;

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

    @Value("${bank.kafka.retry.max-attempts}")
    public int maxAttempts;

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Transaction> kafkaTemplate) {

        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
                        kafkaTemplate,
                        (record, ex) -> new TopicPartition(
                                dltTopic,
                                record.partition())
        );

        ExponentialBackOff backOff = new ExponentialBackOff(initialInterval, multiplier);
        backOff.setMaxInterval(maxInterval);
        backOff.setMaxAttempts(maxAttempts);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                recoverer,
                backOff);

        // CRITICAL: Handle PERMANENT failures (RuntimeException) - send immediately to DLT without retries
        errorHandler.addNotRetryableExceptions(RuntimeException.class);

        errorHandler.setRetryListeners(
                ((record, ex, deliveryAttempt) -> {
                    log.warn("Retry Attempts : {} for record offset = {}, partition = {} | Errors : {} ",
                            deliveryAttempt,
                            record.offset(),
                            record.partition(),
                            ex.getMessage());
                })
        );
        
        return errorHandler;
    }
}

