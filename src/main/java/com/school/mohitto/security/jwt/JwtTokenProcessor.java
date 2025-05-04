package com.school.mohitto.security.jwt;

import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.domain.authentication.Token;
import com.school.mohitto.domain.authentication.TokenProcessor;
import com.school.mohitto.domain.authentication.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProcessor implements TokenProcessor {

    private static final String TOKEN_PREFIX = "Bearer ";
    final JwtProperties jwtProperties;

    @Override
    public String encode(
            LocalDateTime issueTime,
            TokenType tokenType,
            Map<String, Object> privateClaims) {

        final Date issueDate = convertToDate(issueTime);
        final String key = jwtProperties.findTokenKey(tokenType);
        final long expiredHours = jwtProperties.findExpiredHours(tokenType);

        return TOKEN_PREFIX + Jwts.builder()
                .issuedAt(issueDate)
                .expiration(new Date(issueDate.getTime() + expiredHours * 60 * 60 * 1000L))
                .claims(privateClaims)
                .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    private Date convertToDate(LocalDateTime targetTime) {
        return Date.from(targetTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public Optional<Claims> decode(TokenType tokenType, String token) {
        validateBearerToken(token);

        return parseToClaims(tokenType, token);
    }

    private void validateBearerToken(String token) {
        if (token == null || token.isBlank()) {
            throw new CustomException(ErrorCode.TOKEN_EMPTY);
        }

        if (!token.startsWith(TOKEN_PREFIX)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN_BEARER_TYPE);
        }
    }

    private Optional<Claims> parseToClaims(TokenType tokenType, String token) {
        final String key = jwtProperties.findTokenKey(tokenType);
        try {
            final Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(findPureToken(token))
                    .getPayload();

            return Optional.of(claims);
        } catch (CustomException ignored) {
            return Optional.empty();
        }
    }

    private String findPureToken(String token) {
        return token.substring(TOKEN_PREFIX.length());
    }


    @Override
    public Token generateToken(LocalDateTime issueTime, Map<String, Object> privateClaims) {
        final String accessToken = encode(issueTime, TokenType.ACCESS, privateClaims);
        final String refreshToken = encode(issueTime, TokenType.REFRESH, privateClaims);

        return new Token(accessToken, refreshToken);
    }
}
