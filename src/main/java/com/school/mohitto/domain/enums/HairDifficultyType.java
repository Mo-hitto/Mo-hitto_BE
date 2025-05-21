package com.school.mohitto.domain.enums;


import lombok.Getter;

@Getter
public enum HairDifficultyType {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private final String value;

    HairDifficultyType(String value) {
        this.value = value;
    }
}

