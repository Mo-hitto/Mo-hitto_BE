package com.school.mohitto.repository;

import com.school.mohitto.domain.ModelImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelImageRepository extends JpaRepository<ModelImage, Long> , ModelImageRepositoryCustom {
}
