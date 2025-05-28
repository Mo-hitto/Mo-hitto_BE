package com.school.mohitto.service;

import com.school.mohitto.domain.Hair;
import com.school.mohitto.dto.responseDTO.HairResponseListDTO;
import com.school.mohitto.repository.UserHairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HairService {

    private final UserHairRepository userHairRepository;

    public HairResponseListDTO getSavedHairs(Long userId, Pageable pageable) {
        Page<Hair> hairs = userHairRepository.findHairsByUserId(userId, pageable);
        return HairResponseListDTO.from(hairs);
    }
}
