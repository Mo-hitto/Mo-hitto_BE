package com.school.mohitto.dto.requestDTO;

import jakarta.validation.constraints.NotNull;

public record BasicDiagnosisRequest(

        @NotNull(message = "성별 ID는 필수입니다.")
        Long genderId,

        @NotNull(message = "모발 형태 ID는 필수입니다.")
        Long hairTypeId,

        @NotNull(message = "기장 ID는 필수입니다.")
        Long hairLengthId,

        @NotNull(message = "이마 모양 ID는 필수입니다.")
        Long foreheadShapeId,

        @NotNull(message = "광대 모양 ID는 필수입니다.")
        Long checkboneShapeId
) {}

