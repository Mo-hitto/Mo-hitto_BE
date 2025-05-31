package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum HairLengthType {
    SHORT("단발"),
    MEDIUM("중단발"),
    LONG("장발");

    private final String value;

    HairLengthType(String value) {
        this.value = value;
    }
}