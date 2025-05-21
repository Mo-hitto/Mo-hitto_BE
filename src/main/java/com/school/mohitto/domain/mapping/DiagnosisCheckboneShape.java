package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.BaseTimeEntity;
import com.school.mohitto.domain.CheckboneShape;
import com.school.mohitto.domain.Diagnosis;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "D_checkboneshape")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiagnosisCheckboneShape extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Diagnosis_CheckboneShape_id")
    private Long id;

    // 진단과의 연관관계
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    // 광대 모양과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checkboneshape_id")
    private CheckboneShape checkboneShape;

    public static DiagnosisCheckboneShape of(Diagnosis diagnosis, CheckboneShape checkboneShape) {
        DiagnosisCheckboneShape dcs = new DiagnosisCheckboneShape();
        dcs.diagnosis = diagnosis;
        dcs.checkboneShape = checkboneShape;
        return dcs;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

}