package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum SexType {
    MALE("남성"),
    FEMALE("여성");

    private final String value;

    SexType(String value) {
        this.value = value;
    }
}
