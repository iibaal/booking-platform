package com.bookingplatform.accommodation_service.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;
}