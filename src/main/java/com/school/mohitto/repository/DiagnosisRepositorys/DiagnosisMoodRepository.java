package com.school.mohitto.repository.DiagnosisRepositorys;

import com.school.mohitto.domain.mapping.DiagnosisMood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagnosisMoodRepository extends JpaRepository<DiagnosisMood,Long> {
    List<DiagnosisMood> findAllById(Long diagnosisId);
}
