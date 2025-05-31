package com.school.mohitto.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebClientFactory {

    private final WebClient.Builder webClientBuilder;

    public WebClient create(String baseUrl) {
        return webClientBuilder
                .codecs(configurer -> configurer.defaultCodecs()
                .maxInMemorySize(10 * 1024 * 1024))
                .baseUrl(baseUrl)
                .build();
    }
}
