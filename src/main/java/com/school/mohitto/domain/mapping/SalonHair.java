package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.Hair;
import com.school.mohitto.domain.Salon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salon_hairs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalonHair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salon_hair_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_id")
    private Hair hair;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salon_id")
    private Salon salon;
}

