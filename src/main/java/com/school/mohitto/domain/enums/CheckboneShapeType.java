package com.school.mohitto.domain.enums;

public enum CheckboneShapeType {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low"),
    PROMINENT("prominent"),
    SUBTLE("subtle");

    private final String value;

    CheckboneShapeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}