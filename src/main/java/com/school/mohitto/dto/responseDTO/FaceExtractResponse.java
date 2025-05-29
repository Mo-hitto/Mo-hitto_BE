package com.school.mohitto.dto.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "얼굴 분석 결과 응답")
public record FaceExtractResponse(

        @Schema(description = "헤어 타입", example = "곱슬")
        String id,

        @Schema(description = "모발 상태", example = "장발")
        String 모발,

        @Schema(description = "길이", example = "X")
        String 길이,

        @Schema(description = "염색 여부", example = "X")
        String 염색,

        @Schema(description = "피부색 또는 염색 정보", example = "염색 정보 없음")
        String 피부색,

        @Schema(description = "추천 염색 색상 목록", example = "[]")
        List<String> 추천_염색,

        @Schema(description = "이마 모양", example = "M자형")
        String 이마_모양,

        @Schema(description = "광대 형태", example = "도드라짐")
        String 광대,

        @Schema(description = "기분 / 분위기", example = "[\"우아한\", \"따뜻한\", \"부드러운\"]")
        List<String> 기분,

        @Schema(description = "관리 난이도", example = "쉬운")
        String 관리_난이도,

        @Schema(description = "얼굴형", example = "둥근형")
        String 얼굴형,

        @Schema(description = "이마에 대한 평가", example = "크지 않은 편에 속합니다.")
        String 이마_평가,

        @Schema(description = "중안부에 대한 평가", example = "넓은 편에 속합니다.")
        String 중안부_평가,

        @Schema(description = "하안부에 대한 평가", example = "크지 않은 편에 속합니다.")
        String 하안부_평가,

        @Schema(description = "얼굴 분석 총평", example = "전체적으로 부드럽고 온화한 인상을 주며, 친근한 이미지가 강조됩니다.")
        String 얼굴_분석_총평,

        @Schema(description = "상안부 평가", example = "크지 않은 편에 속합니다.")
        String top_rate,

        @Schema(description = "중안부 평가", example = "넓은 편에 속합니다.")
        String middle_rate,

        @Schema(description = "하안부 평가", example = "크지 않은 편에 속합니다.")
        String bottom_rate,

        @Schema(description = "성별", example = "남성")
        String 성별

) {}
