package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.ImpressionType;
import com.school.mohitto.domain.mapping.DiagnosisCheckboneShape;
import com.school.mohitto.domain.mapping.DiagnosisImpression;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "impression")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Impression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "impression_id")
    private Long id;

    @Column(name = "impression_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImpressionType impression;

    @OneToMany(mappedBy = "impression")
    private List<DiagnosisImpression> diagnosisImpressions = new ArrayList<>();
}
