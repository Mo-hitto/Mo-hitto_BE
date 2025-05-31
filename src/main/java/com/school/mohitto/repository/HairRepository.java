package com.school.mohitto.repository;

import com.school.mohitto.domain.Hair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HairRepository  extends JpaRepository<Hair, Long> {

    @Query("""
    SELECT h FROM Hair h
    JOIN h.diagnosis d
    WHERE d.user.id = :userId AND h.isLiked = true
""")
    Page<Hair> findLikedHairsByUserId(@Param("userId") Long userId, Pageable pageable);

}
