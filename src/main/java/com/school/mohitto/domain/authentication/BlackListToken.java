package com.school.mohitto.domain.authentication;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = {"id", "userId", "tokenType", "token"})
@Table(name = "blacklist_token")
public class BlackListToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(nullable = false)
    private String token;

    public BlackListToken(Long userId, TokenType tokenType, String token) {
        this.userId = userId;
        this.tokenType = tokenType;
        this.token = token;
    }
}
