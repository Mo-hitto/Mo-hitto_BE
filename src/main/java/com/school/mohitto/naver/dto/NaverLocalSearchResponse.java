package com.school.mohitto.naver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "네이버 지역 검색 API 응답")
public record NaverLocalSearchResponse(
        @Schema(description = "응답 생성 시간", example = "Tue, 04 Oct 2016 13:10:58 +0900")
        String lastBuildDate,

        @Schema(description = "전체 결과 수", example = "407")
        int total,

        @Schema(description = "시작 인덱스", example = "1")
        int start,

        @Schema(description = "표시할 결과 수", example = "10")
        int display,

        @Schema(description = "검색 결과 항목 리스트")
        List<LocalItem> items
) {}