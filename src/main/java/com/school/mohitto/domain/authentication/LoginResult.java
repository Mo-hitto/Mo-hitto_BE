package com.school.mohitto.domain.authentication;

public record LoginResult (
        Long id,
        String accessToken,
        String refreshToken
){
}
