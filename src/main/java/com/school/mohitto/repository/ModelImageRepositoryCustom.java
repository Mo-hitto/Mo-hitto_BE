package com.school.mohitto.repository;

import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.ModelImage;

public interface ModelImageRepositoryCustom {
    ModelImage findModelImageByDiagnosisFeature(Diagnosis diagnosis);
}
