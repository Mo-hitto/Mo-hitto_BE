package com.school.mohitto.domain;

import com.school.mohitto.domain.mapping.SalonHair;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private List<SalonHair> salonHairs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_image_id")
    private ModelImage modelImage;

    @OneToOne(mappedBy = "hair", cascade = CascadeType.ALL, orphanRemoval = true)
    private CreatedImage createdImage;

    public void updateLike(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public void setCreatedImage(CreatedImage createdImage) {
        this.createdImage = createdImage;

        // 양방향 연관관계 유지
        if (createdImage != null && createdImage.getHair() != this) {
            createdImage.setHair(this);
        }
    }



    @Builder
    public Hair(String name, String explanation, Boolean isLiked, Diagnosis diagnosis, ModelImage modelImage) {
        this.name = name;
        this.explanation = explanation;
        this.isLiked = isLiked;
        this.diagnosis = diagnosis;
        this.modelImage = modelImage;
    }
}