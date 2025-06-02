package com.school.mohitto.dto.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "추천 스타일 및 미용실 정보 응답")
public record FinalRecommandResponse(
        @Schema(description = "추천된 스타일 목록")
        List<StyleHairInfo> recommendations
) {
    @Schema(description = "스타일 정보")
    public record StyleHairInfo(

            @Schema(description = "추천된 헤어스타일 이름", example = "레이어드 컷")
            String style,

            @Schema(description = "추천된 헤어스타일 사진" , example = "s3.jpg")
            Long modelImageId,

            @Schema(description = "스타일 설명", example = "볼륨감 있는 레이어드 스타일로 얼굴형을 살려줍니다.")
            String description,

            @Schema(description = "추천된 미용실 목록")
            List<String> hair_shops

    ) {

    }
}
