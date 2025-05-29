package com.school.mohitto.dto.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "얼굴 분석 결과 응답 DTO")
public record FaceExtractResponse(

        @Schema(description = "모발 타입", example = "곱슬")
        String hair_type,

        @Schema(description = "모발 길이", example = "장발")
        String hair_length,

        @Schema(description = "염색 여부", example = "X")
        String dyed,

        @Schema(description = "피부색 또는 염색 관련 정보", example = "염색 정보 없음")
        String skin,

        @Schema(description = "추천 염색 컬러 리스트", example = "[\"애쉬 브라운\", \"카키 브라운\"]")
        List<String> recs,

        @Schema(description = "이마 형태", example = "M자형")
        String forehead_shape,

        @Schema(description = "광대 형태", example = "도드라짐")
        String cheekbone,

        @Schema(description = "기분/분위기 키워드", example = "[\"우아한\", \"따뜻한\", \"부드러운\"]")
        List<String> mood,

        @Schema(description = "스타일링 난이도", example = "쉬운")
        String difficulty,

        @Schema(description = "얼굴형 평가", example = "둥근형")
        String faceshape_eval,

        @Schema(description = "이마 평가", example = "크지 않은 편에 속합니다.")
        String forehead_eval,

        @Schema(description = "중안부 평가", example = "넓은 편에 속합니다.")
        String central_eval,

        @Schema(description = "하안부 평가", example = "크지 않은 편에 속합니다.")
        String low_eval,

        @Schema(description = "얼굴 분석 총평", example = "전체적으로 부드럽고 온화한 인상을 주며, 친근한 이미지가 강조됩니다.")
        String final_evaluation,

        @Schema(description = "상안부 평가", example = "크지 않은 편에 속합니다.")
        String top_rate,

        @Schema(description = "중안부 평가", example = "넓은 편에 속합니다.")
        String middle_rate,

        @Schema(description = "하안부 평가", example = "크지 않은 편에 속합니다.")
        String bottom_rate,

        @Schema(description = "성별", example = "남성")
        String sex ){

}
