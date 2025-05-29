package com.school.mohitto.dto.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "헤어스타일 추천 요청 DTO")
public record RecommandRequest(

        @Schema(description = "머리 길이", example = "장발")
        String hair_length,

        @Schema(description = "머리 유형", example = "곱슬")
        String hair_type,

        @Schema(description = "성별", example = "남성")
        String sex,

        @Schema(description = "광대뼈 형태", example = "도드라짐")
        String cheekbone,

        @Schema(description = "전달하고 싶은 분위기 리스트", example = "[\"우아한\", \"따뜻한\", \"부드러운\"]")
        List<String> mood,

        @Schema(description = "이마 형태", example = "M자형")
        String forehead_shape,

        @Schema(description = "스타일링 난이도", example = "쉬운")
        String difficulty,

        // @Schema(description = "앞머리 유무", example = "있음")
        // String has_bangs,

        @Schema(description = "얼굴형", example = "타원형")
        String face_shape,

        @Schema(description = "요약 요청사항", example = "전체적으로 볼륨을 살리고 싶어요")
        String summary

) {}
