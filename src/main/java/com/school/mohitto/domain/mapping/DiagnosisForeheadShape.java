package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.ForeheadShape;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "D_foreheadshape")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class DiagnosisForeheadShape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_ForeheadShape_id")
    private Long id;

    // 진단과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    // 이마 모양과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foreheadshape_id")
    private ForeheadShape foreheadShape;
}
