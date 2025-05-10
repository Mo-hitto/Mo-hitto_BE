package com.school.mohitto.domain.authentication;

import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;

public enum OAuth2Type {
    KAKAO,
    ;

    public static OAuth2Type from(String typeName) {
        for (OAuth2Type oauth2Type : values()) {
            if (oauth2Type.name().equalsIgnoreCase(typeName)) {
                return oauth2Type;
            }
        }
        throw new CustomException(ErrorCode.UNSUPPORTED_OAUTH2_TYPE);
    }
}

