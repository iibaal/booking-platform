package com.bookingplatform.accommodation_service.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bookingplatform.accommodation_service.dto.AccommodationDTO;
import com.bookingplatform.accommodation_service.dto.AmenityDTO;
import com.bookingplatform.accommodation_service.dto.CreateAccommodationRequest;
import com.bookingplatform.accommodation_service.entity.Accommodation;
import com.bookingplatform.accommodation_service.entity.Amenity;
import com.bookingplatform.accommodation_service.exception.ResourceNotFoundException;
import com.bookingplatform.accommodation_service.repository.AccommodationRepository;
import com.bookingplatform.accommodation_service.repository.AmenityRepository;

@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AmenityRepository amenityRepository;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository, AmenityRepository amenityRepository) {
        this.accommodationRepository = accommodationRepository;
        this.amenityRepository = amenityRepository;
    }

    @Transactional
    public AccommodationDTO createAccommodation(CreateAccommodationRequest request) {
        Accommodation accommodation = new Accommodation();
        BeanUtils.copyProperties(request, accommodation);

        // Handle amenities
        if (request.getAmenityIds() != null && !request.getAmenityIds().isEmpty()) {
            Set<Amenity> amenities = amenityRepository.findAllById(request.getAmenityIds()).stream()
                    .collect(Collectors.toSet());
            if (amenities.size() != request.getAmenityIds().size()) {
                // Some amenity IDs were not found, handle this error as per business logic
                throw new IllegalArgumentException("One or more provided amenity IDs are invalid.");
            }
            accommodation.setAmenities(amenities);
        }

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        return convertToDto(savedAccommodation);
    }

    @Transactional(readOnly = true)
    public AccommodationDTO getAccommodationById(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accommodation not found with id: " + id));
        return convertToDto(accommodation);
    }

    @Transactional(readOnly = true)
    public List<AccommodationDTO> getAllAccommodations() {
        return accommodationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccommodationDTO updateAccommodation(Long id, CreateAccommodationRequest request) {
        Accommodation existingAccommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accommodation not found with id: " + id));

        BeanUtils.copyProperties(request, existingAccommodation, "id", "createdAt"); // Exclude ID and creation date

        // Update amenities
        if (request.getAmenityIds() != null) {
            Set<Amenity> updatedAmenities = amenityRepository.findAllById(request.getAmenityIds()).stream()
                    .collect(Collectors.toSet());
            if (updatedAmenities.size() != request.getAmenityIds().size()) {
                throw new IllegalArgumentException("One or more provided amenity IDs are invalid.");
            }
            existingAccommodation.setAmenities(updatedAmenities);
        } else {
            existingAccommodation.setAmenities(new HashSet<>()); // Clear amenities if null is passed
        }

        Accommodation updatedAccommodation = accommodationRepository.save(existingAccommodation);
        return convertToDto(updatedAccommodation);
    }

    @Transactional
    public void deleteAccommodation(Long id) {
        if (!accommodationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Accommodation not found with id: " + id);
        }
        accommodationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<AccommodationDTO> searchAccommodations(String city, String country) {
        List<Accommodation> accommodations;
        if (city != null && country != null) {
            accommodations = accommodationRepository.findByCityAndCountry(city, country);
        } else if (city != null) {
            accommodations = accommodationRepository.findByCity(city);
        } else if (country != null) {
            accommodations = accommodationRepository.findByCountry(country);
        } else {
            accommodations = accommodationRepository.findAll(); // Return all if no search criteria
        }
        return accommodations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AccommodationDTO convertToDto(Accommodation accommodation) {
        AccommodationDTO dto = new AccommodationDTO();
        BeanUtils.copyProperties(accommodation, dto);
        if (accommodation.getAmenities() != null) {
            dto.setAmenities(accommodation.getAmenities().stream()
                    .map(this::convertAmenityToDto)
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    private AmenityDTO convertAmenityToDto(Amenity amenity) {
        AmenityDTO dto = new AmenityDTO();
        BeanUtils.copyProperties(amenity, dto);
        return dto;
    }
}
