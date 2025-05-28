package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.SexType;
import com.school.mohitto.domain.mapping.DiagnosisSex;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sex")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sex_id")
    private Long id;

    @Column(name = "sex_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SexType sex;

    // 연관관계
    @OneToMany(mappedBy = "sex")
    private List<DiagnosisSex> diagnosisSexes = new ArrayList<>();

}
