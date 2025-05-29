package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum ForeheadShapeType {
    ROUND("동근형"),
    M_SHAPE("M자형"),
    SQUARE("네모형");

    private final String value;

    ForeheadShapeType(String value) {
        this.value = value;
    }

}