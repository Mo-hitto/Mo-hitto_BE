package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.BaseTimeEntity;
import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.Sex;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "D_sex")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiagnosisSex extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiagnosisSex_id")
    private Long id;

    // 진단과의 연관관계 (1:1)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;


    // 성별과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sex_id")
    private Sex sex;

    public static DiagnosisSex of(Diagnosis diagnosis, Sex sex) {
        DiagnosisSex dg = new DiagnosisSex();
        dg.diagnosis = diagnosis;
        dg.sex = sex;
        return dg;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }
}
