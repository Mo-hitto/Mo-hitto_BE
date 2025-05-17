package com.school.mohitto.domain.enums;

public enum HairLengthType {
    SHORT("short"),
    MEDIUM("medium"),
    LONG("long"),
    EXTRA_LONG("extra_long");

    private final String value;

    HairLengthType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}