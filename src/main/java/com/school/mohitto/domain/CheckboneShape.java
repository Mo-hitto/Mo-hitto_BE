package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.CheckboneShapeType;
import com.school.mohitto.domain.mapping.DiagnosisCheckboneShape;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CheckboneShape")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckboneShape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkbone_id")
    private Long id;

    @Column(name = "CheckboneShapeType", nullable = false)
    @Enumerated(EnumType.STRING)
    private CheckboneShapeType shape;

    // 연관관계
    @OneToMany(mappedBy = "checkboneShape")
    private List<DiagnosisCheckboneShape> diagnosisCheckboneShapes = new ArrayList<>();
}