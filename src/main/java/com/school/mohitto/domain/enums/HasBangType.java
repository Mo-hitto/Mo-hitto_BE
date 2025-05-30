package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum HasBangType{
    TRUE("있음"),
    FALSE("없음");

    private final String value;

    HasBangType(String value) {
        this.value = value;
    }
}
