package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.BaseTimeEntity;
import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.Impression;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "D_impression")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiagnosisImpression extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_impression_id")
    private Long id;

    // 진단과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    // 인상과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "impression_id")
    private Impression impression;

    public static DiagnosisImpression of(Diagnosis diagnosis, Impression impression) {
        DiagnosisImpression di = new DiagnosisImpression();
        di.setDiagnosis(diagnosis);
        di.setImpression(impression);
        return di;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setImpression(Impression impression) {
        this.impression = impression;
    }
}
