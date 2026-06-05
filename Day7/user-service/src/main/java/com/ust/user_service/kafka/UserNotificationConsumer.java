package com.ust.user_service.kafka;

import com.ust.user_service.dto.OrderStatusEvent;
import com.ust.user_service.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UserNotificationConsumer {

    @Autowired
    UserService userService;

    @KafkaListener(
            topics = "${user.kafka.topics.order-status}",
            groupId = "${user.kafka.groups.user-group}"
    )
    public void consumeUserNotification(OrderStatusEvent event) {
        log.info("Received user order notification for order : {}", event.getOrderId());
        userService.updateOrderStausNotification(event);
    }
}
