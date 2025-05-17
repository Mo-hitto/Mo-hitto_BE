package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.Gender;
import com.school.mohitto.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "D_gender")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiagnosisGender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiagnosisGender_id")
    private Long id;

    // 진단과의 연관관계 (1:1)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;


    // 성별과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    private Gender gender;
}
