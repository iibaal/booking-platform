package com.bookingplatform.accommodation_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RoomDTO {
    private Long id;
    private Long accommodationId;
    private String roomNumber;
    private String type;
    private BigDecimal pricePerNight;
    private Integer maxOccupancy;
    private String description;
}