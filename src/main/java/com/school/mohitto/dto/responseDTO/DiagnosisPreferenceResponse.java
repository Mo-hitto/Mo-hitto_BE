package com.school.mohitto.dto.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "선호도 진단 저장 응답")
public record DiagnosisPreferenceResponse(
        @Schema(description = "진단 ID", example = "42") Long diagnosisId,
        @Schema(description = "저장된 인상 개수", example = "3") int impressionCount
) {
    public static DiagnosisPreferenceResponse of(Long diagnosisId, int impressionCount) {
        return new DiagnosisPreferenceResponse(diagnosisId, impressionCount);
    }
}
