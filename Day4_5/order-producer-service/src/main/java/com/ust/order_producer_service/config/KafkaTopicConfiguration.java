package com.ust.order_producer_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Value("${topics.json-topic}")
    private String jsonTopicName;

    @Value("${topics.string-topic}")
    private String stringTopicName;

    @Value("${topics.string-topic}")
    private String orderStatusTopicName;

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name(stringTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderJsonEventsTopic() {
        return TopicBuilder.name(jsonTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderEventsTopic() {
        return TopicBuilder.name(orderStatusTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
