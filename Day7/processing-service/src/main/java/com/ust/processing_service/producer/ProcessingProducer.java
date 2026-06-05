package com.ust.processing_service.producer;

import com.ust.processing_service.dto.OrderStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class ProcessingProducer {

    @Value("${processing.kafka.topics.status}")
    private String ORDER_STATUS_TOPIC;

    @Autowired
    KafkaTemplate<String, OrderStatusEvent> kafkaTemplate;

    public void updateOrderStatus(OrderStatusEvent event) {
        log.info("Producing and status update event to a topic : {}", ORDER_STATUS_TOPIC);
        try {
            kafkaTemplate.send(ORDER_STATUS_TOPIC, event);
            log.info("Published order status update event to a topic : {}", ORDER_STATUS_TOPIC);
        } catch (Exception e) {
            log.error("Failed to publish status update event for order : {}", event.getOrderId());
            throw new RuntimeException(e);
        }
    }



}
