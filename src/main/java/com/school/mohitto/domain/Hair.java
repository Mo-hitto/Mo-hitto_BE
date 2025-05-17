package com.school.mohitto.domain;

import com.school.mohitto.domain.mapping.UserHair;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hairs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hair_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "styling_method")
    private String method;


    @OneToMany(mappedBy = "hair", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModelImage> modelImages = new ArrayList<>();

    @OneToMany(mappedBy = "hair", cascade = CascadeType.ALL)
    private List<UserHair> userHairs = new ArrayList<>();
    // 생성자, 빌더, 비즈니스 메서드
}