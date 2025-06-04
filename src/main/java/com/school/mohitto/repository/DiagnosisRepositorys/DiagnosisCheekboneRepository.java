package com.school.mohitto.repository.DiagnosisRepositorys;

import com.school.mohitto.domain.mapping.DiagnosisCheekbone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiagnosisCheekboneRepository extends JpaRepository<DiagnosisCheekbone, Long> {
    Optional<DiagnosisCheekbone> findByDiagnosisId(Long diagnosisId);
}
