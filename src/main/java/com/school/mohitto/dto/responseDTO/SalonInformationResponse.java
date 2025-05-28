package com.school.mohitto.dto.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "미용실 정보 응답 DTO")
public record SalonInformationResponse(

        @Schema(description = "상호명 또는 제목", example = "홍대 미용실 헤어살롱")
        String title,

        @Schema(description = "상세 링크 URL", example = "http://example.com/detail-page")
        String link,

        @Schema(description = "간단한 설명", example = "트렌디한 스타일 전문 미용실입니다.")
        String description,

        @Schema(description = "지번 주소", example = "서울특별시 중구 을지로3가 229-1")
        String address,

        @Schema(description = "전화번호", example = "02-123-4567")
        String telephone,

        @Schema(description = "X좌표 (경도)", example = "1269783")
        String mapx,

        @Schema(description = "Y좌표 (위도)", example = "3759123")
        String mapy

) {}