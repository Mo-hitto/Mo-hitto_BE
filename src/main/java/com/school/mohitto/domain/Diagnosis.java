package com.school.mohitto.domain;

import com.school.mohitto.domain.mapping.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Diagnosis")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diagnosis extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_id")
    private Long id;

    // 사용자와의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 진단 성별 정보와의 연관관계
    @OneToOne(mappedBy = "diagnosis", cascade = CascadeType.ALL, orphanRemoval = true)
    private DiagnosisSex diagnosisSex;

    @OneToOne(mappedBy = "diagnosis", cascade = CascadeType.ALL, orphanRemoval = true)
    private DiagnosisHairType diagnosisHairType;

    // 진단 이마 모양 정보와의 연관관계 (추가됨)
    @OneToOne(mappedBy = "diagnosis", cascade = CascadeType.ALL, orphanRemoval = true)
    private DiagnosisForeheadShape diagnosisForeheadShape;

    // 진단 헤어 기장 정보와의 연관관계 (추가됨)
    @OneToOne(mappedBy = "diagnosis", cascade = CascadeType.ALL, orphanRemoval = true)
    private DiagnosisHairLength diagnosisHairLength;

    @OneToOne(mappedBy = "diagnosis", cascade = CascadeType.ALL, orphanRemoval = true)
    private DiagnosisCheekbone diagnosisCheekbone;

    @OneToOne(mappedBy = "diagnosis", cascade = CascadeType.ALL, orphanRemoval = true)
    private DiagnosisHairDifficulty diagnosisHairDifficulty;

    @OneToOne(mappedBy = "diagnosis", cascade = CascadeType.ALL, orphanRemoval = true)
    private UploadImage uploadImage;

    @OneToMany(mappedBy = "diagnosis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiagnosisMood> diagnosisMoods = new ArrayList<>();


    @OneToMany(mappedBy = "diagnosis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreatedImage> createdImages = new ArrayList<>();

    public static Diagnosis create(User user) {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.user = user;
        return diagnosis;
    }

    public void addDiagnosisGender(DiagnosisSex diagnosisSex) {
        this.diagnosisSex = diagnosisSex;
        diagnosisSex.setDiagnosis(this);
    }

    public void addDiagnosisHairType(DiagnosisHairType diagnosisHairType) {
        this.diagnosisHairType = diagnosisHairType;
        diagnosisHairType.setDiagnosis(this);
    }

    public void addDiagnosisHairLength(DiagnosisHairLength diagnosisHairLength) {
        this.diagnosisHairLength = diagnosisHairLength;
        diagnosisHairLength.setDiagnosis(this);
    }

    public void addDiagnosisForeheadShape(DiagnosisForeheadShape diagnosisForeheadShape) {
        this.diagnosisForeheadShape = diagnosisForeheadShape;
        diagnosisForeheadShape.setDiagnosis(this);
    }

    public void addDiagnosisCheckboneShape(DiagnosisCheekbone diagnosisCheekbone) {
        this.diagnosisCheekbone = diagnosisCheekbone;
        diagnosisCheekbone.setDiagnosis(this);
    }

    public void addDiagnosisHairDifficulty(DiagnosisHairDifficulty diagnosisHairDifficulty) {
        this.diagnosisHairDifficulty = diagnosisHairDifficulty;
        diagnosisHairDifficulty.setDiagnosis(this);
    }

    public void addDiagnosisImpression(DiagnosisMood diagnosisMood) {
        this.diagnosisMoods.add(diagnosisMood);
        diagnosisMood.setDiagnosis(this);
    }






}
