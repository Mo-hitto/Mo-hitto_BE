package com.school.mohitto.dto.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "사용자의 얼굴 이미지 및 스타일 정보 요청")
public record FaceExtractRequest(

        @Schema(description = "사용자 이미지 URL", example = "https://mohitto-s3.s3.ap-northeast-2.amazonaws.com/face/fe8921e0-c456-4d71-b55e-6900a191d0bb.jpeg")
        String user_image_url,

        @Schema(description = "머리 길이", example = "장발")
        String hair_length,

        @Schema(description = "머리 유형", example = "곱슬")
        String hair_type,

        @Schema(description = "염색 여부 (O / X)", example = "X")
        String dyed,

        @Schema(description = "이마 형태", example = "M자형")
        String forehead_shape,

        @Schema(description = "광대뼈 형태", example = "도드라짐")
        String cheekbone,

        @Schema(description = "전달하고 싶은 분위기 리스트", example = "[\"우아한\", \"따뜻한\", \"부드러운\"]")
        List<String> mood,

        @Schema(description = "스타일링 난이도", example = "쉬운")
        String difficulty,

        @Schema(description = "성별", example = "남성")
        String sex,

        @Schema(description = "앞머리 유무", example = "있음")
        String has_bangs
) {}
