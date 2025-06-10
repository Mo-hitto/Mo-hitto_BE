package com.school.mohitto.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "created_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CreatedImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "created_image_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "hair_id", nullable = true)
    private Hair hair;


    @Column(name = "url", columnDefinition = "TEXT")
    private String createdImageUrl;

    @Builder
    public CreatedImage(String createdImageUrl) {
        this.createdImageUrl = createdImageUrl;
    }

    public CreatedImage(Hair hair, String createdImageUrl) {
        this.hair = hair;
        this.createdImageUrl = createdImageUrl;
    }
}