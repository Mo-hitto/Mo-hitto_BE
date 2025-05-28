package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.CheekboneType;
import com.school.mohitto.domain.mapping.DiagnosisCheekbone;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cheekbone")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cheekbone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cheekbone_id")
    private Long id;

    @Column(name = "CheekboneType", nullable = false)
    @Enumerated(EnumType.STRING)
    private CheekboneType shape;

    // 연관관계
    @OneToMany(mappedBy = "cheekbone")
    private List<DiagnosisCheekbone> diagnosisCheekbones = new ArrayList<>();
}