package com.ust.order_producer_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("order-string-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderJsonEventsTopic() {
        return TopicBuilder.name("order-json-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
