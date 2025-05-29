package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.MoodType;
import com.school.mohitto.domain.mapping.DiagnosisMood;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Mood")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mood_id")
    private Long id;

    @Column(name = "mood_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MoodType moodType;

    @OneToMany(mappedBy = "mood")
    private List<DiagnosisMood> diagnosisMoods = new ArrayList<>();
}
