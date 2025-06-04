package com.school.mohitto.repository.DiagnosisRepositorys;

import com.school.mohitto.domain.mapping.DiagnosisForeheadShape;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiagnosisForeheadShapeRepository extends JpaRepository<DiagnosisForeheadShape, Long> {
    Optional<DiagnosisForeheadShape> findByDiagnosisId(Long diagnosisId);
}
