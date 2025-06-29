package com.bookingplatform.accommodation_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.time.LocalTime;
import java.util.Set;

@Data
public class CreateAccommodationRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "Country is mandatory")
    private String country;

    private Double latitude;
    private Double longitude;

    @Min(value = 1, message = "Star rating must be at least 1")
    @NotNull(message = "Star rating is mandatory")
    private Integer starRating;

    @Email(message = "Contact email must be a valid email format")
    private String contactEmail;

    private String contactPhone;

    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    private Set<Long> amenityIds; // IDs of existing amenities to associate
}