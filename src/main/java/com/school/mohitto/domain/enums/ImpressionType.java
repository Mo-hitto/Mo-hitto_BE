package com.school.mohitto.domain.enums;

public enum ImpressionType {

    SHARP("sharp"),
    SOFT("soft"),
    NEUTRAL("neutral");

    private final String value;

    ImpressionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
