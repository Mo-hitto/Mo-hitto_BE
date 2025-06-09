package com.school.mohitto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "upload_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    @Column(name = "url", columnDefinition = "TEXT")
    private String uploadImageUrl;

    public UploadImage(String uploadImageUrl) {
        this.uploadImageUrl = uploadImageUrl;
    }

    public UploadImage(Diagnosis diagnosis,String uploadImageUrl) {
        this.diagnosis = diagnosis;
        this.uploadImageUrl = uploadImageUrl;
    }
}
