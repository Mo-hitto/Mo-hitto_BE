package com.school.mohitto.domain.authentication;

import io.jsonwebtoken.Claims;

import java.util.Map;

public record PrivateClaims(Long userId) {
    private static final String USER_ID_KEY_NAME = "userId";

    public static PrivateClaims from(Claims claims) {
        final Long userId = claims.get(USER_ID_KEY_NAME, Long.class);

        return new PrivateClaims(userId);
    }

    public Map<String, Object> toMap() {
        return Map.of(
                USER_ID_KEY_NAME, userId
        );
    }
}
