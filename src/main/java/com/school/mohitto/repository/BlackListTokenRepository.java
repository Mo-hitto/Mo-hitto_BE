package com.school.mohitto.repository;

import com.school.mohitto.domain.authentication.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {
    boolean existsByUserIdAndToken(Long userId, String token);
}
