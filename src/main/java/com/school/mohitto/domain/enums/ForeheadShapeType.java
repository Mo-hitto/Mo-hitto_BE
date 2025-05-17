package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum ForeheadShapeType {
    ROUND("round"),
    SQUARE("square"),
    TRIANGLE("triangle"),
    HEART("heart"),
    OVAL("oval");

    private final String value;

    ForeheadShapeType(String value) {
        this.value = value;
    }

}