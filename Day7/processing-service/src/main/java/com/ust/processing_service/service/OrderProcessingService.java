package com.ust.processing_service.service;

import com.ust.processing_service.dto.OrderEvent;
import com.ust.processing_service.dto.OrderStatusEvent;
import com.ust.processing_service.producer.ProcessingProducer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderProcessingService {

    @Autowired
    ProcessingProducer producer;

    public void checkInventory(OrderEvent event) {
       String orderStatus = event.getQuantity() > 5 ? "REJECTED" : "APPROVED";
       event.setStatus(orderStatus);
       String remark = event.getQuantity() > 5
               ? "Order Validated. Inventory check Failed for " + event.getOrderId() + " : "+ event.getProductName() +"Order quantity is greater than 5."
               : "Order Validated. Inventory check passed for " + event.getOrderId() + " : "+ event.getProductName();

        OrderStatusEvent stausEvent = OrderStatusEvent.builder()
                .orderId(event.getOrderId())
                .userId(event.getUserId())
                .status(event.getStatus())
                .remark(remark)
                .build();
       log.info("Checked inventory and updated order status for Order : {}", event.getOrderId());

       producer.updateOrderStatus(stausEvent);
    }

    public void processFailedOrder(OrderEvent event) {
        String orderStatus = "FAILED";
        String remark = "Order not processed due to unexpected exceptions!";

        OrderStatusEvent stausEvent = OrderStatusEvent.builder()
                .orderId(event.getOrderId())
                .userId(event.getUserId())
                .status(orderStatus)
                .remark(remark)
                .build();

        log.info("Updating status of failed order : {}", event.getOrderId());

        producer.updateOrderStatus(stausEvent);
    }
}
