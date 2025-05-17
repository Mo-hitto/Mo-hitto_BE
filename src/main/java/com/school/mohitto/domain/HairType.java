package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.HairTypeEnum;
import com.school.mohitto.domain.mapping.DiagnosisHairType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Hair_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HairType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hair_type_id")
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private HairTypeEnum type;

    // 연관관계
    @OneToMany(mappedBy = "hairType")
    private List<DiagnosisHairType> diagnosisHairTypes = new ArrayList<>();
}
