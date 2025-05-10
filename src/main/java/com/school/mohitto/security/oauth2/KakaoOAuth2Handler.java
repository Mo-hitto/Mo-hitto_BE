package com.school.mohitto.security.oauth2;

import com.school.mohitto.config.WebClientFactory;
import com.school.mohitto.domain.authentication.OAuth2Handler;
import com.school.mohitto.domain.authentication.OAuth2Type;
import com.school.mohitto.domain.authentication.OAuth2UserInfo;
import com.school.mohitto.domain.authentication.dto.KakaoTokenResponseDto;
import com.school.mohitto.domain.authentication.dto.KakaoUserInfoResponseDto;
import com.school.mohitto.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class KakaoOAuth2Handler implements OAuth2Handler {

    private static final String KAKAO_PROVIDE_AUTH_CODE_URL = "https://kauth.kakao.com/oauth/authorize";
    private static final String KAKAO_PROVIDE_ACCESS_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_PROVIDE_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final KakaoOAuth2Properties kakaoOAuth2Properties;
    private final WebClientFactory webClientFactory;

    @Override
    public OAuth2Type getSupportingOAuth2Type() {
        return OAuth2Type.KAKAO;
    }

    @Override
    public String provideAuthCodeRequestUrl() {
        return UriComponentsBuilder
                .fromUriString(KAKAO_PROVIDE_AUTH_CODE_URL)
                .queryParam("client_id", kakaoOAuth2Properties.clientId())
                .queryParam("redirect_uri", kakaoOAuth2Properties.redirectUri())
                .queryParam("response_type", "code")
                .queryParam("scope", String.join(",", kakaoOAuth2Properties.scope()))
                .toUriString();
    }

    @Override
    public String getOAuth2AccessToken(String authorizationCode) {
        KakaoTokenResponseDto response = webClientFactory.create(KAKAO_PROVIDE_ACCESS_TOKEN_URL).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", kakaoOAuth2Properties.clientId())
                        .queryParam("redirect_uri", kakaoOAuth2Properties.redirectUri())
                        .queryParam("code", authorizationCode)
                        .queryParam("client_secret", kakaoOAuth2Properties.clientSecret())
                        .build(true)
                )
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

        return response.access_token();
    }

    @Override
    public OAuth2UserInfo getUserInfo(String accessToken) {

        KakaoUserInfoResponseDto response = webClientFactory.create(KAKAO_PROVIDE_USER_INFO_URL).get()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

        return new OAuth2UserInfo(response.id(),OAuth2Type.KAKAO,
                (String) response.properties().get("nickname"),
                (String) response.properties().get("profile_image")
        );
    }
}
