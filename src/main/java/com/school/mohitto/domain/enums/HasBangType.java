package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum HasBangType{
    YES("있음"),
    NO("없음"),
    NONE("상관없음");

    private final String value;

    HasBangType(String value) {
        this.value = value;
    }
}
