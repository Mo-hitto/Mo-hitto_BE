package com.school.mohitto.controller;

import com.school.mohitto.domain.authentication.AuthUserInfo;
import com.school.mohitto.dto.responseDTO.HairListResponse;
import com.school.mohitto.dto.responseDTO.MypageUserInfoResponse;
import com.school.mohitto.dto.responseDTO.*;
import com.school.mohitto.exception.annotation.PageConstraint;
import com.school.mohitto.security.annotation.AuthUser;
import com.school.mohitto.service.HairService;
import com.school.mohitto.service.SalonService;
import com.school.mohitto.service.HairService;
import com.school.mohitto.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Tag(name = "Mypage", description = "마이페이지 API")
public class MypageController {

    private final HairService hairService;
    private final UserService userService;
    private final SalonService salonService;

    @Operation(summary = "저장한 헤어스타일 목록 조회")
    @GetMapping("hair/saved")
    public HairListResponse getSavedHairs(
            @AuthUser AuthUserInfo authUserInfo,
            @ParameterObject
            @PageableDefault(size = 6, sort = "createdDate", direction = Sort.Direction.DESC)
            @Valid @PageConstraint Pageable pageable
    ) {
        HairListResponse result = hairService.getSavedHairs(authUserInfo.userId(), pageable);
        return result;
    }


    @Operation(
        summary = "마이페이지 유저 정보 조회",
        description = "마이페이지 상단에 보여줄 유저의 닉네임, 프로필이미지 등을 조회합니다."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "401", description = "인증 실패"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/userInfo")
    public MypageUserInfoResponse getMypageUserInfo(
            @AuthUser AuthUserInfo authUserInfo
    ){
        return userService.getMypageUserInfo(authUserInfo.userId());
    }



    @GetMapping("/salons/saved")
    @Operation(summary = "내가 저장한 미용실 목록 조회")
    public List<SalonResponse> getMySavedSalons(
            @AuthUser AuthUserInfo authUserInfo
    ) {
        List<SalonResponse> salonsResponse = salonService.getSavedSalons(authUserInfo.userId());
        return salonsResponse;
    }

    @DeleteMapping("/salons/saved/{salonId}")
    @Operation(summary = "저장한 미용실 삭제", description = "저장된 미용실을 하트를 다시 눌러 삭제합니다.")
    public DeletedSalonResponse deleteSavedSalon(
            @AuthUser AuthUserInfo authUserInfo,
            @Parameter(description = "삭제할 미용실 ID") @PathVariable Long salonId
    ) {
        salonService.deleteSavedSalon(authUserInfo.userId(), salonId);
        DeletedSalonResponse response = new DeletedSalonResponse(salonId);
        return response;
    }
}

