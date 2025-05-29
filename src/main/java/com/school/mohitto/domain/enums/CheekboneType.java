package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum CheekboneType {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low"),
    PROMINENT("prominent"),
    SUBTLE("subtle");

    private final String value;

    CheekboneType(String value) {
        this.value = value;
    }

}