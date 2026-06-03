package com.ust.booking_service.entity;

import lombok.Data;

//@Entity
//@Table(name = "bookings")
@Data
public class Booking {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    private String movie;
    private String seatNumber;
}
