package com.bookingplatform.accommodation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingplatform.accommodation_service.entity.Accommodation;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> findByCity(String city);

    List<Accommodation> findByCountry(String country);

    List<Accommodation> findByCityAndCountry(String city, String country);
    // You can add more complex query methods here or use @Query annotations
}