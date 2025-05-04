package com.school.mohitto.domain.authentication;

import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public interface TokenProcessor {
    String encode(LocalDateTime issueTime, TokenType tokenType, Map<String, Object> privateClaims);
    Optional<Claims> decode(TokenType tokenType, String token);
    Token generateToken(LocalDateTime issueTime, Map<String, Object> privateClaims);
}
