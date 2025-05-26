package com.school.mohitto.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cors")
public record CorsProperties(
        String[] allowedOrigins
){
}
