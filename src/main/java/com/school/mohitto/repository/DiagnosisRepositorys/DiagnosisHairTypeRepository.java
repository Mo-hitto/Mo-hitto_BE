package com.school.mohitto.repository.DiagnosisRepositorys;

import com.school.mohitto.domain.mapping.DiagnosisHairType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiagnosisHairTypeRepository extends JpaRepository<DiagnosisHairType, Long> {
    Optional<DiagnosisHairType> findByDiagnosisId(Long diagnosisId);
}
