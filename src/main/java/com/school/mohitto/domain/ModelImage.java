package com.school.mohitto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "model_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModelImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_id")
    private Hair hair;

    @Column(name = "url", length = 255)
    private String uploadImageUrl;
}
