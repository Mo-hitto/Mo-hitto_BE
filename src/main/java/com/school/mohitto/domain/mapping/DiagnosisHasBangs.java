package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.HasBangs;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "D_has_bangs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiagnosisHasBangs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_hasBangs_id")
    private Long id;

    // 진단과의 연관관계 (1:1)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;


    // 성별과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "has_bangs_id")
    private HasBangs hasBangs;

    public  static DiagnosisHasBangs of(Diagnosis diagnosis, HasBangs hasBangs) {
        DiagnosisHasBangs diagnosisHasBangs = new DiagnosisHasBangs();
        diagnosisHasBangs.diagnosis = diagnosis;
        diagnosisHasBangs.hasBangs = hasBangs;
        return diagnosisHasBangs;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }
}
