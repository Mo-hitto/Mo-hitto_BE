package com.school.mohitto.service;

import com.school.mohitto.domain.Hair;
import com.school.mohitto.dto.responseDTO.HairLikeToggleResponse;
import com.school.mohitto.dto.responseDTO.HairListResponse;
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

@Service
@RequiredArgsConstructor
public class HairService {

    private final UserHairRepository userHairRepository;
    private final UserRepository userRepository;
    private final HairRepository hairRepository;


    @Transactional
    public HairLikeToggleResponse toggleLikeStatus(Long hairId) {
        Hair hair = hairRepository.findById(hairId)
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_NOT_FOUND));

        boolean newLikeState = !hair.getIsLiked();
        hair.updateLike(newLikeState);

        return new HairLikeToggleResponse(newLikeState ? hair.getId() : null);
    }

    @Transactional(readOnly = true)
    public HairListResponse getSavedHairs(Long userId, Pageable pageable) {
        Page<Hair> page = hairRepository.findLikedHairsByUserId(userId, pageable);
        return HairListResponse.from(page);
    }









}
