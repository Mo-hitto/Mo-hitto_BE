package com.school.mohitto.domain;

import com.school.mohitto.domain.mapping.SalonHair;
import com.school.mohitto.domain.mapping.UserSalon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "link")
    private String link;

    // 연관관계
    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private List<SalonHair> salonHairs = new ArrayList<>();

    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private List<UserSalon> userSalons = new ArrayList<>();

    @Builder
    public Salon(String name, String description, String address, String telephone, String link) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.telephone = telephone;
        this.link = link;
    }
}