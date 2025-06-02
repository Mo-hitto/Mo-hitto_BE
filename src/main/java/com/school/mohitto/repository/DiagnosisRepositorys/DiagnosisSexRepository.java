package com.school.mohitto.repository.DiagnosisRepositorys;

import com.school.mohitto.domain.mapping.DiagnosisSex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiagnosisSexRepository extends JpaRepository<DiagnosisSex,Long> {
    Optional<DiagnosisSex> findByDiagnosisId(Long diagnosisId);
}
