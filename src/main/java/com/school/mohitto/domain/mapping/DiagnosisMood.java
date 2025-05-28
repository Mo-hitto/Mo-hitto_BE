package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.BaseTimeEntity;
import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.Mood;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "D_Mood")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiagnosisMood extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_Mood_id")
    private Long id;

    // 진단과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    // 인상과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mood_id")
    private Mood mood;

    public static DiagnosisMood of(Diagnosis diagnosis, Mood mood) {
        DiagnosisMood di = new DiagnosisMood();
        di.setDiagnosis(diagnosis);
        di.setMood(mood);
        return di;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }
}
