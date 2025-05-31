package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum ForeheadShapeType {
    EGG("계란형"),         // 계란형
    HEART("하트형"),       // 하트형
    SQUARE("네모형"),      // 네모형
    ROUND("둥근형"),       // 둥근형
    LONG("긴형");

    private final String value;

    ForeheadShapeType(String value) {
        this.value = value;
    }

}