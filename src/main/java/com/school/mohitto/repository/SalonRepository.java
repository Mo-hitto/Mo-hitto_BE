package com.school.mohitto.repository;

import com.school.mohitto.domain.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalonRepository extends JpaRepository<Salon, Long> {
    Optional<Salon> findByAddress(String address);
}
