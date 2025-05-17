package com.school.mohitto.domain;

import com.school.mohitto.domain.mapping.UserSalon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Salon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salon_id")
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction;

    @Column(name = "address", length = 200)
    private String Address;

    // 연관관계
    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private List<UserSalon> userSalons = new ArrayList<>();
}