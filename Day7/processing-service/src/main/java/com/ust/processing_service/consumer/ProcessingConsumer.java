package com.ust.processing_service.consumer;

import com.ust.processing_service.dto.OrderEvent;
import com.ust.processing_service.service.OrderProcessingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ProcessingConsumer {

    @Autowired
    OrderProcessingService orderProcessingService;

    @RetryableTopic( attempts = "3")
    @KafkaListener(
            topics = "${processing.kafka.topics.order}",
            groupId = "${processing.kafka.groups.order}"
    )
    public void orderCreatedEvent(OrderEvent event, Acknowledgment acknowledgment) {
        if("FAILED".equalsIgnoreCase(event.getProductName())) {
            throw new IllegalStateException("Failed order due to unexpected exceptions!");
        }

        log.error("Received order creation event for order ID : {}", event.getOrderId());
        //Check inventory
        orderProcessingService.checkInventory(event);

        // Manual Commit
        acknowledgment.acknowledge();
    }

    @DltHandler
    public void handleDtlTopics(OrderEvent event, Acknowledgment acknowledgment) {
        log.info("** DLT Topic received => {}", event.toString());

        //Check inventory
        orderProcessingService.processFailedOrder(event);

        // Manual Commit
        acknowledgment.acknowledge();
    }
}
