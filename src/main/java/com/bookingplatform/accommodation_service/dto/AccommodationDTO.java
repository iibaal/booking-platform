package com.bookingplatform.accommodation_service.dto;

import java.time.LocalTime;
import java.util.Set;

import lombok.Data;

@Data
public class AccommodationDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
    private Integer starRating;
    private String contactEmail;
    private String contactPhone;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private Set<AmenityDTO> amenities; // Embed AmenityDTOs
}
