package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.HairDifficultyType;
import com.school.mohitto.domain.mapping.DiagnosisHairDifficulty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hair_difficulty")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HairDifficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "difficulty_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_type", nullable = false, unique = true)
    private HairDifficultyType type;

    @OneToMany(mappedBy = "hairDifficulty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiagnosisHairDifficulty> diagnosisHairDifficulties = new ArrayList<>();
}
