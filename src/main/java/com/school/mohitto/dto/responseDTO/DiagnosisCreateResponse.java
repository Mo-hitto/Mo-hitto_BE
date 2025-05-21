package com.school.mohitto.dto.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "기초 진단 저장 응답")
public record DiagnosisCreateResponse(
        @Schema(description = "생성된 진단 ID", example = "42")
        Long diagnosisId
) {
    public static DiagnosisCreateResponse from(Long id) {
        return new DiagnosisCreateResponse(id);
    }


}
