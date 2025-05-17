package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
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

}