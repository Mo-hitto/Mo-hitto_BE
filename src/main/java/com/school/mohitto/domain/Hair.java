package com.school.mohitto.domain;

import com.school.mohitto.domain.mapping.SalonHair;
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
public class Hair extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hair_id")
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "explanation")
    private String explanation;


    @Column(name = "is_liked")
    private Boolean isLiked;


    @OneToMany(mappedBy = "hair", cascade = CascadeType.ALL)
    private List<UserHair> userHairs = new ArrayList<>();

    @OneToMany(mappedBy = "hair", cascade = CascadeType.ALL)
    private List<SalonHair> salonHairs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_image_id")
    private ModelImage modelImage;

    public void updateLike(boolean isLiked) {
        this.isLiked = isLiked;
    }

}