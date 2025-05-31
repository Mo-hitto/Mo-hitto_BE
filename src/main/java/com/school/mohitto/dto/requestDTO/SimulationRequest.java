package com.school.mohitto.dto.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record SimulationRequest(
        @Schema(name = "진단 id")
        Long diagnosisId
) {
}
