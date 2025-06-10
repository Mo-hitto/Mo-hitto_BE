package com.school.mohitto.repository;

import com.school.mohitto.domain.CreatedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CreatedImageRepository extends JpaRepository<CreatedImage, Long> {

    @Query("""
    SELECT ci FROM CreatedImage ci
    JOIN ci.hair h
    JOIN h.diagnosis d
    JOIN d.user u
    WHERE u.id = :userId
""")
    List<CreatedImage> findAllByUserIdIndirect(@Param("userId") Long userId);

    // 좋아요 토글을 위한 존재 여부 확인용
    @Query("""
        SELECT ci FROM CreatedImage ci
        JOIN ci.hair h
        WHERE h.id = :hairId AND ci.createdImageUrl = :imageUrl
    """)
    Optional<CreatedImage> findByHairIdAndImageUrl(@Param("hairId") Long hairId, @Param("imageUrl") String imageUrl);

}
