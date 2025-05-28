package com.school.mohitto.naver.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "지역 검색 결과 항목")
public record LocalItem(
        @Schema(description = "상호명", example = "조선옥")
        String title,

        @Schema(description = "상세 링크", example = "http://...")
        String link,

        @Schema(description = "업종 분류", example = "한식>육류,고기요리")
        String category,

        @Schema(description = "설명", example = "연탄불 한우갈비 전문점")
        String description,

        @Schema(description = "전화번호", example = "02-123-4567")
        String telephone,

        @Schema(description = "지번 주소", example = "서울특별시 중구 을지로3가 229-1")
        String address,

        @Schema(description = "도로명 주소", example = "서울특별시 중구 을지로15길 6-5")
        String roadAddress,

        @Schema(description = "X좌표 (경도)", example = "311277")
        String mapx,

        @Schema(description = "Y좌표 (위도)", example = "552097")
        String mapy
) {}
