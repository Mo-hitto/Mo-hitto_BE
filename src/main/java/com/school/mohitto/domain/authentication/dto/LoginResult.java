package com.school.mohitto.domain.authentication.dto;

public record LoginResult (
        Long id,
        String accessToken,
        String refreshToken
){
}
