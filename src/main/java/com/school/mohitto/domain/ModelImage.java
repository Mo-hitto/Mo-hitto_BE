package com.school.mohitto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "model_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModelImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_image_id")
    private Long id;

    @Column(name = "upload_image_url", length = 255)
    private String uploadImageUrl;

    @OneToMany(mappedBy = "modelImage")
    private List<Hair> hairs = new ArrayList<>();
}

