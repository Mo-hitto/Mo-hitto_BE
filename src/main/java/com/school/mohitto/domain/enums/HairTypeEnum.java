package com.school.mohitto.domain.enums;

public enum HairTypeEnum {
    STRAIGHT("straight", "직모"),
    COILY("coily", "심한 곱슬"),
    CURLY("curly", "곱슬");

    private final String code;
    private final String description;


    HairTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
