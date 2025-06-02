package com.school.mohitto.repository.DiagnosisRepositorys;

import com.school.mohitto.domain.mapping.DiagnosisHairLength;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiagnosisHairLengthRepository extends JpaRepository<DiagnosisHairLength,Long> {
    Optional<DiagnosisHairLength> findByDiagnosisId(Long diagnosisId);
}
