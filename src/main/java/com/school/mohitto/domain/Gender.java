package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.GenderType;
import com.school.mohitto.domain.mapping.DiagnosisGender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Gender")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gendertype", nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    // 연관관계
    @OneToMany(mappedBy = "gender")
    private List<DiagnosisGender> diagnosisGenders = new ArrayList<>();

}
