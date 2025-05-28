package com.school.mohitto.service;

import com.school.mohitto.domain.Hair;
import com.school.mohitto.domain.User;
import com.school.mohitto.domain.mapping.UserHair;
import com.school.mohitto.dto.responseDTO.HairResponseListDTO;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.repository.HairRepository;
import com.school.mohitto.repository.UserHairRepository;
import com.school.mohitto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.school.mohitto.exception.code.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class HairService {

    private final UserHairRepository userHairRepository;
    private final UserRepository userRepository;
    private final HairRepository hairRepository;

    public HairResponseListDTO getSavedHairs(Long userId, Pageable pageable) {
        Page<Hair> hairs = userHairRepository.findHairsByUserId(userId, pageable);
        return HairResponseListDTO.from(hairs);
    }

    @Transactional
    public void deleteSavedHair(Long userId, Long hairId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Hair hair = hairRepository.findById(hairId)
                .orElseThrow(() -> new CustomException(HAIR_NOT_FOUND));

        UserHair userHair = userHairRepository.findByUserAndHair(user, hair)
                .orElseThrow(() -> new CustomException(SAVED_HAIR_NOT_FOUND));

        userHairRepository.delete(userHair);
    }


}
