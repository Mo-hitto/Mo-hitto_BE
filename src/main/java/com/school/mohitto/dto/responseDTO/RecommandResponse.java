package com.school.mohitto.dto.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "추천 스타일 결과 응답")
public record RecommandResponse(

        @Schema(description = "추천된 헤어스타일 이름", example = "레이어드 컷")
        String style,

        @Schema(description = "스타일 설명", example = "볼륨감 있는 레이어드 스타일로 얼굴형을 살려줍니다.")
        String description,

        @Schema(description = "추천된 미용실 목록")
        List<HairShopInfo> hair_shops

) {
    @Schema(description = "추천된 미용실 정보")
    public record HairShopInfo(

            @Schema(description = "미용실 이름", example = "미용실 A")
            String hair_shop,

            @Schema(description = "최종 추천 스타일", example = "레이어드")
            String final_dic_style,

            @Schema(description = "경도", example = "127.01")
            double longitude,

            @Schema(description = "위도", example = "37.50")
            double latitude,

            @Schema(description = "최종 메뉴 가격", example = "75000")
            int final_menu_price,

            @Schema(description = "리뷰 개수", example = "120")
            int review_count,

            @Schema(description = "평균 평점", example = "4.8")
            double mean_score

    ) {}
}
