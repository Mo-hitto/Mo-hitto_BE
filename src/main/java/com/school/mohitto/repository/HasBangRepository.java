package com.school.mohitto.repository;

import com.school.mohitto.domain.HasBangs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HasBangRepository extends JpaRepository<HasBangs, Long> {
}
