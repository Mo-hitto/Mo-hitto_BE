package com.school.mohitto.service;

import com.school.mohitto.domain.Hair;
import com.school.mohitto.domain.User;
import com.school.mohitto.domain.mapping.UserHair;
import com.school.mohitto.dto.responseDTO.HairLikeToggleResponse;
import com.school.mohitto.dto.responseDTO.HairResponseList;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
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

    public HairResponseList getSavedHairs(Long userId, Pageable pageable) {
        Page<Hair> hairs = userHairRepository.findHairsByUserId(userId, pageable);
        return HairResponseList.from(hairs);
    }

    @Transactional
    public HairLikeToggleResponse toggleLikeStatus(Long hairId) {
        Hair hair = hairRepository.findById(hairId)
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_NOT_FOUND));

        boolean newLikeState = !hair.getIsLiked();
        hair.updateLike(newLikeState);

        return new HairLikeToggleResponse(newLikeState ? hair.getId() : null);
    }








}
