package com.ust.order_producer_service.service;

import com.ust.order_producer_service.entity.OrderEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderProducerService {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topics.string-topic}")
    private String stringTopic;

    @Value("${topics.json-topic}")
    private String jsonTopic;

    @Value("${topics.status-topic}")
    private String statusTopic;

    public void sendOrderToKafka(Long id) {
        // Logic to create an order and send it to Kafka
        String orderMessage = "Order with ID: " + id + " has been created.";
        kafkaTemplate.send(stringTopic, orderMessage);
    }

    public void sendOrderToKafka(OrderEvent order) {
        // Logic to create an order and send it to Kafka
        kafkaTemplate.send(jsonTopic, order);
    }

    public void sendOrderToKafkaWithStatus(OrderEvent order) {
        // Logic to create an order with a status and send it to Kafka
        log.info("Order with status is being sent to Kafka: {}", order.toString());

        publishStatus("ORDER_CREATED");
        scheduledStatus("ORDER_PROCESSING", 5000);
        scheduledStatus("ORDER_PACKED", 5000);
        scheduledStatus("ORDER_SHIPPED", 5000);
        scheduledStatus("ORDER_DELIVERED", 5000);
    }

    private void scheduledStatus(String orderStatus, int delay) {
        try {
            Thread.sleep(delay);
            publishStatus(orderStatus);
        } catch (InterruptedException e) {
            log.error("Error in scheduledStatus: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void publishStatus(String status) {
        log.info("Order with status i.e. {} is being sent to Kafka: ", status);
        kafkaTemplate.send(statusTopic, status);
    }
}
