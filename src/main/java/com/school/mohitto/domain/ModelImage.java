package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.HairLengthType;
import com.school.mohitto.domain.enums.HairTypeEnum;
import com.school.mohitto.domain.enums.HasBangType;
import com.school.mohitto.domain.enums.SexType;
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

    @Column(length = 40)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex_type")
    private SexType sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "hair_type")
    private HairTypeEnum hairTypeEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "hasbang_type")
    private HasBangType hasBangType;

    @Enumerated(EnumType.STRING)
    @Column(name = "hair_length")
    private HairLengthType hairLength;

    @Column(name = "upload_image_url", length = 255)
    private String uploadImageUrl;

    @OneToMany(mappedBy = "modelImage")
    private List<Hair> hairs = new ArrayList<>();
}

