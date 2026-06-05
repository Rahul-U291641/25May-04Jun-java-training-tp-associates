package com.ust.processing_service.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaNewTopics {

    @Value("${processing.kafka.topics.status}")
    private String ORDER_STATUS_TOPIC;

    public NewTopic orderStatusTopic(){
        return TopicBuilder.name(ORDER_STATUS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
