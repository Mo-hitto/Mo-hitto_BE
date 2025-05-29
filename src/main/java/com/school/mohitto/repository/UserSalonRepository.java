package com.school.mohitto.repository;

import com.school.mohitto.domain.Salon;
import com.school.mohitto.domain.User;
import com.school.mohitto.domain.mapping.UserSalon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSalonRepository extends JpaRepository<UserSalon,Long> {
    boolean existsByUserAndSalon(User user, Salon salon);

    Optional<UserSalon> findByUserAndSalon(User user, Salon salon);

    @Query("SELECT us.salon FROM UserSalon us WHERE us.user.id = :userId")
    List<Salon> findSalonsByUserId(@Param("userId") Long userId);

}
