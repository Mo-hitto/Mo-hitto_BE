package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum HairTypeEnum {
    STRAIGHT("직모"),
    COILY("반곱슬"),
    CURLY("곱슬");

    private final String value;


    HairTypeEnum(String value) {
        this.value = value;
    }

}
