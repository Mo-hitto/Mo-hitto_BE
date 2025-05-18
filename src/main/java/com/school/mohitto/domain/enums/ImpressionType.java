package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum ImpressionType {

    CUTE("cute"),
    FRESH("fresh"),
    SEXY("sexy"),
    SPORTY("sporty"),
    CHIC("chic"),
    FEMININE("feminine"),
    BRIGHT("bright"),
    TRENDY("trendy"),
    ELEGANT("elegant"),
    HIP("hip"),
    COOL("cool"),
    LUXURIOUS("luxurious"),
    CALM("calm"),
    NATURAL("natural"),
    INTELLECTUAL("intellectual"),
    WARM("warm"),
    UNIQUE("unique"),
    DANDY("dandy");

    private final String value;

    ImpressionType(String value) {
        this.value = value;
    }
}
