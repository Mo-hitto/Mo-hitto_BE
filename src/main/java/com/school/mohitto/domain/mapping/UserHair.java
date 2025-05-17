package com.school.mohitto.domain.mapping;

import com.school.mohitto.domain.BaseTimeEntity;
import com.school.mohitto.domain.Hair;
import com.school.mohitto.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_hairs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHair extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_hair_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_id")
    private Hair hair;
}