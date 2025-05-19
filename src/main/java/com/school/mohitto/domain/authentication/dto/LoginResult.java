package com.school.mohitto.domain.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResult (
        @Schema(description = "사용자 id", example = "1")
        Long id,
        @Schema(description = "서비스 Access Token", example = "Bearer access-token-abcdefghijklmn")
        String accessToken,
        @Schema(description = "서비스 Refresh Token", example = "Bearer access-token-abcdefghijklmn")
        String refreshToken
){
}
