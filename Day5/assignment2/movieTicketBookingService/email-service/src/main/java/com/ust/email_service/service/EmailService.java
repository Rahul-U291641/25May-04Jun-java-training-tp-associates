package com.ust.email_service.service;

import com.ust.email_service.entity.Booking;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Log4j2
public class EmailService {

    @Value("${topics.email-topic}")
    private String emailTopic;

    @KafkaListener(
            topics = "${topics.email-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void sendEmailNotification(Booking booking) {
        // Logic to send email notification based on the received booking information
        log.info("Received booking information for email notification: {}", booking.toString());
        // Here you can add code to send an email, e.g., using JavaMailSender
        // Print a movie ticket structure using sysout and booking information
        printMovieTicket(booking);
    }

    private void printMovieTicket(Booking booking) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);

        System.out.println("\n" + "═".repeat(50));
        System.out.println("║" + " ".repeat(48) + "║");
        System.out.println("║" + centerText("🎬 MOVIE TICKET 🎬", 48) + "║");
        System.out.println("║" + " ".repeat(48) + "║");
        System.out.println("╠" + "═".repeat(48) + "╣");
        System.out.println("║ Booking ID    : " + String.format("%-31s", booking.getBookingId()) + "║");
        System.out.println("║ Movie         : " + String.format("%-31s", booking.getMovie()) + "║");
        System.out.println("║ Seat Number   : " + String.format("%-31s", booking.getSeatNumber()) + "║");
        System.out.println("║ Issue Date    : " + String.format("%-31s", timestamp) + "║");
        System.out.println("║" + " ".repeat(48) + "║");
        System.out.println("╠" + "═".repeat(48) + "╣");
        System.out.println("║" + centerText("Enjoy Your Movie!", 48) + "║");
        System.out.println("║" + " ".repeat(48) + "║");
        System.out.println("═".repeat(50) + "\n");
    }

    private String centerText(String text, int width) {
        int totalPadding = width - text.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }
}
