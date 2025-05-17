package com.school.mohitto.domain.enums;

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

    public String getValue() {
        return value;
    }
}