package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum CheekboneType {
    PROMINENT("많이 도드라짐"),
    SLIGHTLY_PROMINENT("약간 도드라짐"),
    NEUTRAL("눈에 띄지 않음");

    private final String value;

    CheekboneType(String value) {
        this.value = value;
    }

}