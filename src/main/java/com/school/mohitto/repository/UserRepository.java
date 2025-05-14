package com.school.mohitto.repository;

import com.school.mohitto.domain.User;
import com.school.mohitto.domain.authentication.OAuth2Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauth2IdAndOauth2Type(String oauth2Id, OAuth2Type oauth2Type);
}
