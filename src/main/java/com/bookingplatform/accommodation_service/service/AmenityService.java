package com.bookingplatform.accommodation_service.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookingplatform.accommodation_service.dto.AmenityDTO;
import com.bookingplatform.accommodation_service.entity.Amenity;
import com.bookingplatform.accommodation_service.exception.ResourceNotFoundException;
import com.bookingplatform.accommodation_service.repository.AmenityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmenityService {
    private final AmenityRepository amenityRepository;

    @Autowired
    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    @Transactional
    public AmenityDTO createAmenity(AmenityDTO amenityDTO) {
        if (amenityRepository.existsByName(amenityDTO.getName())) {
            throw new IllegalArgumentException("Amenity with name '" + amenityDTO.getName() + "' already exists.");
        }
        Amenity amenity = new Amenity();
        BeanUtils.copyProperties(amenityDTO, amenity, "id");
        return convertToDto(amenityRepository.save(amenity));
    }

    @Transactional(readOnly = true)
    public AmenityDTO getAmenityById(Long id) {
        Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Amenity not found with id: " + id));
        return convertToDto(amenity);
    }

    @Transactional(readOnly = true)
    public List<AmenityDTO> getAllAmenities() {
        return amenityRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AmenityDTO updateAmenity(Long id, AmenityDTO amenityDTO) {
        Amenity existingAmenity = amenityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Amenity not found with id: " + id));

        // Check for name conflict if name is being changed
        if (!existingAmenity.getName().equalsIgnoreCase(amenityDTO.getName())
                && amenityRepository.existsByName(amenityDTO.getName())) {
            throw new IllegalArgumentException("Amenity with name '" + amenityDTO.getName() + "' already exists.");
        }

        BeanUtils.copyProperties(amenityDTO, existingAmenity, "id", "createdAt"); // Exclude ID and creation date
        return convertToDto(amenityRepository.save(existingAmenity));
    }

    @Transactional
    public void deleteAmenity(Long id) {
        if (!amenityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Amenity not found with id: " + id);
        }
        amenityRepository.deleteById(id);
    }

    private AmenityDTO convertToDto(Amenity amenity) {
        AmenityDTO dto = new AmenityDTO();
        BeanUtils.copyProperties(amenity, dto);
        return dto;
    }

}
