package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum HairLengthType {
    SHORT("short"),
    MEDIUM("medium"),
    LONG("long");

    private final String value;

    HairLengthType(String value) {
        this.value = value;
    }
}