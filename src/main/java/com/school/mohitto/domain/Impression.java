package com.school.mohitto.domain;

import com.school.mohitto.domain.enums.ImpressionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "impression")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Impression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "impression_id")
    private Long id;

    @Column(name = "impression_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImpressionType impression;
}
