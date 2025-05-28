package com.school.mohitto.domain.enums;

import lombok.Getter;

@Getter
public enum MoodType {

    STYLISH("세련된"),
    SOFT("부드러운"),
    CLEAN("깔끔한"),
    CUTE("귀여운"),
    NEAT("단정한"),
    ELEGANT("우아한"),
    UNIQUE("독특한"),
    LOVELY("사랑스러운"),
    LUXURIOUS("고급스러운"),
    CALM("차분한"),
    WARM("따뜻한"),
    INTENSE("강렬한");

    private final String value;

    MoodType(String value) {
        this.value = value;
    }
}
