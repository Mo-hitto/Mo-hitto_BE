package com.school.mohitto.domain;

import com.school.mohitto.domain.authentication.OAuth2Type;
import com.school.mohitto.domain.mapping.UserHair;
import com.school.mohitto.domain.mapping.UserSalon;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = {"id", "name"})
@Table(name = "users")
public class User {

    private static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false)
    private String profileImageUrl;

    @Column(name = "oauth2_id", nullable = false)
    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth2_type", nullable = false)
    private OAuth2Type oauth2Type;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserHair> userHairs = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Diagnosis> diagnosis = new ArrayList<>();

    // 저장된 미용실 추가
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSalon> userSalons = new ArrayList<>();

    public User(String name, String profileImageUrl, String oauth2Id, OAuth2Type oauth2Type) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.oauth2Id = oauth2Id;
        this.oauth2Type = oauth2Type;
    }

}
