package com.bookingplatform.accommodation_service.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingplatform.accommodation_service.dto.AmenityDTO;
import com.bookingplatform.accommodation_service.service.AmenityService;

@RestController
@RequestMapping("/api/v1/amenities")
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @PostMapping
    public ResponseEntity<AmenityDTO> createAmenity(@Valid @RequestBody AmenityDTO amenityDTO) {
        AmenityDTO createdAmenity = amenityService.createAmenity(amenityDTO);
        return new ResponseEntity<>(createdAmenity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityDTO> getAmenityById(@PathVariable Long id) {
        AmenityDTO amenity = amenityService.getAmenityById(id);
        return ResponseEntity.ok(amenity);
    }

    @GetMapping
    public ResponseEntity<List<AmenityDTO>> getAllAmenities() {
        List<AmenityDTO> amenities = amenityService.getAllAmenities();
        return ResponseEntity.ok(amenities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AmenityDTO> updateAmenity(@PathVariable Long id, @Valid @RequestBody AmenityDTO amenityDTO) {
        AmenityDTO updatedAmenity = amenityService.updateAmenity(id, amenityDTO);
        return ResponseEntity.ok(updatedAmenity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        amenityService.deleteAmenity(id);
        return ResponseEntity.noContent().build();
    }
}
