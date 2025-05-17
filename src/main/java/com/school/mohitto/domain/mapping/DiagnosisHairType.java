package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.HairType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "D_hairtype")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiagnosisHairType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_hairtype_id")
    private Long id;

    // 진단과의 연관관계
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    // 모발형태와의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hairtype_id")
    private HairType hairType;
}
