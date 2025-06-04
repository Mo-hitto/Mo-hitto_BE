package com.school.mohitto.repository.DiagnosisRepositorys;

import com.school.mohitto.domain.mapping.DiagnosisHairDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiagnosisHairDifficultyRepository extends JpaRepository<DiagnosisHairDifficulty, Long> {
    Optional<DiagnosisHairDifficulty> findByDiagnosisId(Long diagnosisId);
}
