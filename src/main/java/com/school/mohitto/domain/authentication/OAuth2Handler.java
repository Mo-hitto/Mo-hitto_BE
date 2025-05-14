package com.school.mohitto.domain.authentication;

public interface OAuth2Handler {
    OAuth2Type getSupportingOAuth2Type();
    String provideAuthCodeRequestUrl();
    String getOAuth2AccessToken(String authorizationCode);
    OAuth2UserInfo getUserInfo(String accessToken);
}
