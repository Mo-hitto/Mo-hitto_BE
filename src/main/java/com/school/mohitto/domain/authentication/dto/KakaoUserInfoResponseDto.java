package com.school.mohitto.domain.authentication.dto;

import java.util.Map;

public record KakaoUserInfoResponseDto (
        String id,
        Map<String, Object> properties,
        Map<String, Object> kakao_account
){
}
