package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum HairTypeEnum {
    STRAIGHT("straight"),
    COILY("coily"),
    CURLY("curly");

    private final String value;


    HairTypeEnum(String value) {
        this.value = value;
    }

}
