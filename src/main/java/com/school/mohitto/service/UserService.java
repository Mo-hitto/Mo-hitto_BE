package com.school.mohitto.service;

import com.school.mohitto.domain.User;
import com.school.mohitto.dto.responseDTO.MypageUserInfoResponse;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public MypageUserInfoResponse getMypageUserInfo(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return new MypageUserInfoResponse(user.getName(), user.getEmail());
    }
}
