package com.school.mohitto.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cors")
public record CorsProperties(String allowedOrigins) {
    public String[] getAllowedOrigins() {
        return allowedOrigins != null ? allowedOrigins.split(",") : new String[0];
    }
}
