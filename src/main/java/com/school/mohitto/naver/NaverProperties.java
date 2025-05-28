package com.school.mohitto.naver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("naver")
public record NaverProperties(
        String clientId,
        String secret
) {
}
