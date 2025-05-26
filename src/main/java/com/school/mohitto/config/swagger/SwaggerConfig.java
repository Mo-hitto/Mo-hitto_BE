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

    public static final String SERVICE_SECURITY_SCHEME_NAME = "ServiceAuth";
    public static final String OAUTH_SECURITY_SCHEME_NAME = "KakaoAuth";
    public static final String MOHITTO_SERVER_IP = "http://43.203.208.49:8080";

    @Bean
    public OpenAPI mohittoApi() {
        return new OpenAPI()
                .info(apiInfo())
                .components(components())
                .addServersItem(new Server().url("http://localhost:8080").description("로컬"));
    }

    private Info apiInfo() {
        return new Info()
                .title("Mo:hitto server API")
                .description("Mo:hitto API 명세서")
                .version("1.0.0");
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(SERVICE_SECURITY_SCHEME_NAME, serviceSecurityScheme())
                .addSecuritySchemes(OAUTH_SECURITY_SCHEME_NAME,oauthSecurityScheme());
    }

    private SecurityScheme serviceSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("서비스에서 발급한 Bearer Token");
    }

    private SecurityScheme oauthSecurityScheme() {
        final OAuthFlow authorizationCodeFlow = new OAuthFlow()
                .authorizationUrl("https://kauth.kakao.com/oauth/authorize")
                .tokenUrl("https://kauth.kakao.com/oauth/token");

        final OAuthFlows oauthFlows = new OAuthFlows().authorizationCode(authorizationCodeFlow);

        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(oauthFlows)
                .description("Kakao OAuth 인증");
    }
}