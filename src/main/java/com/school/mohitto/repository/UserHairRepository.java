package com.school.mohitto.repository;

import com.school.mohitto.domain.Hair;
import com.school.mohitto.domain.mapping.UserHair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserHairRepository extends JpaRepository<UserHair, Long> {

    @Query("SELECT uh.hair FROM UserHair uh WHERE uh.user.id = :userId")
    Page<Hair> findHairsByUserId(@Param("userId") Long userId, Pageable pageable);
}
