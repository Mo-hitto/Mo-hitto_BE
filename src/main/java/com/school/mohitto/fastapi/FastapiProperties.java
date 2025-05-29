package com.school.mohitto.fastapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("fastapi")
public record FastapiProperties(
        String extractFace,
        String graphRag,
        String hairTransfer
) {
}
