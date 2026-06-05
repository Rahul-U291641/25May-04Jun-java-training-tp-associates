package com.ust.order_service.producer;

import com.ust.order_service.dto.OrderEvent;
import com.ust.order_service.exception.customExceptions.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class OrderProducer {
    @Value("${order.kafka.topics.order}")
    private String ORDER_TOPIC;

    @Autowired
    KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishOrderCreatedEvent(OrderEvent event) {
        log.info("Received a order event to publish!");
        try {
            kafkaTemplate.send(ORDER_TOPIC, event).get();
        } catch (Exception e) {
            log.error("Kafka publish failed", e);
            throw new ServiceException("Kafka publish failed : " + e.getMessage());
        }
    }
}
