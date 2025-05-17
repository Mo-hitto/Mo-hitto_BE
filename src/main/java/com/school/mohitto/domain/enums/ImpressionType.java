package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum ImpressionType {

    SHARP("sharp"),
    SOFT("soft"),
    NEUTRAL("neutral");

    private final String value;

    ImpressionType(String value) {
        this.value = value;
    }

}
