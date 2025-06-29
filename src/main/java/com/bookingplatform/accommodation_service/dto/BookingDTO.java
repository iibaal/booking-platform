package com.bookingplatform.accommodation_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.bookingplatform.accommodation_service.entity.Booking;

@Data
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numGuests;
    private BigDecimal totalPrice;
    private Booking.BookingStatus bookingStatus;
    private Booking.PaymentStatus paymentStatus;
    private LocalDateTime bookingDate;
    private String confirmationCode;
}
