package com.school.mohitto.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public static final String SERVICE_SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI mohittoApi() {
        return new OpenAPI()
                .info(apiInfo())
                .components(components())
                .addSecurityItem(new SecurityRequirement().addList(SERVICE_SECURITY_SCHEME_NAME))// 여기에 이름 매칭
                .addServersItem(new Server().url("/"));
    }

    private Info apiInfo() {
        return new Info()
                .title("Mo:hitto server API")
                .description("Mo:hitto API 명세서")
                .version("1.0.0");
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(SERVICE_SECURITY_SCHEME_NAME, new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .description("Bearer Token"));
    }
}