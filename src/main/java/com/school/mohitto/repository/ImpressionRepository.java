package com.school.mohitto.repository;

import com.school.mohitto.domain.Impression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpressionRepository extends JpaRepository<Impression, Long> {
}
