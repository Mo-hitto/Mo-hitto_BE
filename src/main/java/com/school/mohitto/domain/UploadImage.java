package com.school.mohitto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "upload_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadImage extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    @Column(name = "url", length = 255)
    private String imageUrl;

    public static UploadImage of(Diagnosis diagnosis, String imageUrl) {
        UploadImage image = new UploadImage();
        image.diagnosis = diagnosis;
        image.imageUrl = imageUrl;
        return image;
    }
}
