package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.BaseTimeEntity;
import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.HairDifficulty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "d_hairdifficulty")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiagnosisHairDifficulty extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_difficulty_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "difficulty_id")
    private HairDifficulty hairDifficulty;

    public static DiagnosisHairDifficulty of(Diagnosis diagnosis, HairDifficulty difficulty) {
        DiagnosisHairDifficulty dhd = new DiagnosisHairDifficulty();
        dhd.diagnosis = diagnosis;
        dhd.hairDifficulty = difficulty;
        return dhd;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }
}
