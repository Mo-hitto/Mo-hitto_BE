package com.school.mohitto.domain.enums;


import lombok.Getter;

@Getter
public enum HairDifficultyType {
    EASY("쉬움"),
    MEDIUM("보통"),
    HARD("어려움");

    private final String label;

    HairDifficultyType(String label) {
        this.label = label;
    }
}

