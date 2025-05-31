package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.HasBangType;
import com.school.mohitto.domain.enums.SexType;
import com.school.mohitto.domain.mapping.DiagnosisHasBangs;
import com.school.mohitto.domain.mapping.DiagnosisSex;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "has_bangs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HasBangs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "has_bangs_id")
    private Long id;

    @Column(name = "has_bang_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private HasBangType hasBangType;

    // 연관관계
    @OneToMany(mappedBy = "hasBangs")
    private List<DiagnosisHasBangs> diagnosisHasBangs = new ArrayList<>();
}
