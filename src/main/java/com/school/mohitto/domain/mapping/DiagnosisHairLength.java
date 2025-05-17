package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.BaseTimeEntity;
import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.HairLength;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "D_hairlength")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiagnosisHairLength extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Diagnosis_HairLength_id")
    private Long id;

    // 진단과의 연관관계
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    // 헤어 기장과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hairlength_id")
    private HairLength hairLength;
}