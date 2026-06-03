package com.ust.booking_service.service;

import com.ust.booking_service.entity.Booking;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BookingService {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topics.payment-topic}")
    private String paymentTopic;

    public boolean createBooking(Booking booking) {
        log.info("Received booking request: {}", booking.toString());
        try {
            Thread.sleep(5000);
            log.info("Processing booking for movie: {}, seat: {}", booking.getMovie(), booking.getSeatNumber());
            Thread.sleep(3000);
            log.info("Booking confirmed! Please complete the payment for movie: {}, seat: {}", booking.getMovie(), booking.getSeatNumber());
            Thread.sleep(2000);
            log.info("Raised a payment request for movie: {}, seat: {}", booking.getMovie(), booking.getSeatNumber());
            kafkaTemplate.send(paymentTopic, booking);
        } catch (InterruptedException e) {
            log.error("Error occurred while processing booking request: {}", e.getMessage());
            return false;
        }

        return true;
    }
}
