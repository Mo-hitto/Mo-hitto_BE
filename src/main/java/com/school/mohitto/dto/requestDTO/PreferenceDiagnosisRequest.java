package com.school.mohitto.dto.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PreferenceDiagnosisRequest(

        @NotEmpty(message = "하나 이상의 인상을 선택해야 합니다.")
        List<@NotNull(message = "인상 ID는 null일 수 없습니다.") Long> impressionIds,

        @NotNull(message = "손질 난이도 ID는 필수입니다.")
        Long difficultyId
) {}

