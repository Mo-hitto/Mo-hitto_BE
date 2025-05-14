package com.school.mohitto.domain.authentication.dto;

public record KakaoTokenResponseDto(
        String token_type,
        String access_token,
        String id_token,
        String expires_in,
        String refresh_token,
        String refresh_token_expires_in,
        String scope
) {
}
