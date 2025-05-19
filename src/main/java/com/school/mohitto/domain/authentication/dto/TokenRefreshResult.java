package com.school.mohitto.domain.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenRefreshResult(
        @Schema(description = "서비스 Access Token", example = "Bearer access-token-abcdefghijklmn")
        String accessToken,
        @Schema(description = "서비스 Refresh Token", example = "Bearer refresh-token-opqrstuvwxyz")
        String refreshToken) {
}