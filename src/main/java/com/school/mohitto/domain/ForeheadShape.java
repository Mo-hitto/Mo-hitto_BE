package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.ForeheadShapeType;
import com.school.mohitto.domain.mapping.DiagnosisForeheadShape;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ForeheadShape")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForeheadShape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forehead_id")
    private Long id;

    @Column(name = "foreheadshape_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ForeheadShapeType shape;

    // 연관관계
    @OneToMany(mappedBy = "foreheadShape")
    private List<DiagnosisForeheadShape> diagnosisForeheadShapes = new ArrayList<>();
}
