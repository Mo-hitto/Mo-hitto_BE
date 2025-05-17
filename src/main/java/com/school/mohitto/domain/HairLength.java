package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.HairLengthType;
import com.school.mohitto.domain.mapping.DiagnosisHairLength;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HairLength")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HairLength {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hairlength_id")
    private Long id;

    @Column(name = "hairlength_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private HairLengthType hairLength;

    // 연관관계
    @OneToMany(mappedBy = "hairLength")
    private List<DiagnosisHairLength> diagnosisHairLengths = new ArrayList<>();
}
