package com.school.mohitto.domain.authentication;


public record OAuth2UserInfo(
        String oauth2Id,
        OAuth2Type oauth2Type,
        String nickname,
        String email,
        String profileImageUrl
) {
}
