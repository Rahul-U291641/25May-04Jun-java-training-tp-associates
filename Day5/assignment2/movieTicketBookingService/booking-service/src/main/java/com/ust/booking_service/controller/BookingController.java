package com.ust.booking_service.controller;

import com.ust.booking_service.dto.ApiResponse;
import com.ust.booking_service.entity.Booking;
import com.ust.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping
    public ApiResponse<Booking> createBooking(@RequestBody Booking booking) {
        boolean isBooked = bookingService.createBooking(booking);
        if (isBooked) {
            return new ApiResponse<>(true, "Booking successful", booking);
        } else {
            return new ApiResponse<>(false, "Booking failed for movie: " + booking.getMovie() + ", seat: " + booking.getSeatNumber(), null);
        }
    }
}
