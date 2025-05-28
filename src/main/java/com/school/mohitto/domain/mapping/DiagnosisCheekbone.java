package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.BaseTimeEntity;
import com.school.mohitto.domain.Cheekbone;
import com.school.mohitto.domain.Diagnosis;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "D_cheekbone")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiagnosisCheekbone extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Diagnosis_Cheekbone_id")
    private Long id;

    // 진단과의 연관관계
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    // 광대 모양과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cheekbone_id")
    private Cheekbone cheekbone;

    public static DiagnosisCheekbone of(Diagnosis diagnosis, Cheekbone cheekbone) {
        DiagnosisCheekbone dcs = new DiagnosisCheekbone();
        dcs.diagnosis = diagnosis;
        dcs.cheekbone = cheekbone;
        return dcs;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

}