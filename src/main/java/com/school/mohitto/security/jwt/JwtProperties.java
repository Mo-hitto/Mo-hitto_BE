package com.school.mohitto.security.jwt;

import com.school.mohitto.security.TokenType;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("token.jwt")
public record JwtProperties (
        String accessKey,
        String refreshKey,
        long accessExpiredHours,
        long refreshExpiredHours
){
    public String findTokenKey(TokenType tokenType) {
        if (TokenType.ACCESS == tokenType) {
            return accessKey;
        }

        return refreshKey;
    }

    public Long findExpiredHours(TokenType tokenType) {
        if (TokenType.ACCESS == tokenType) {
            return accessExpiredHours;
        }

        return refreshExpiredHours;
    }
}
