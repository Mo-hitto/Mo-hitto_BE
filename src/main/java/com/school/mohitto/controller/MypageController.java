package com.school.mohitto.controller;

import com.school.mohitto.domain.authentication.AuthUserInfo;
import com.school.mohitto.dto.responseDTO.HairResponseList;
import com.school.mohitto.dto.responseDTO.MypageUserInfoResponse;
import com.school.mohitto.exception.annotation.PageConstraint;
import com.school.mohitto.security.annotation.AuthUser;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Tag(name = "Mypage", description = "마이페이지 API")
public class MypageController {

    private final HairService hairService;
    private final UserService userService;

    @Operation(summary = "저장한 헤어스타일 목록 조회")
    @GetMapping("hair/saved")
    public HairResponseList getSavedHairs(
            @AuthUser AuthUserInfo authUserInfo,
            @ParameterObject
            @PageableDefault(size = 6, sort = "createdDate", direction = Sort.Direction.DESC)
            @Valid @PageConstraint Pageable pageable
    ) {
        HairResponseList result = hairService.getSavedHairs(authUserInfo.userId(), pageable);
        return result;
    }

    @Operation(
            summary = "저장한 헤어스타일 삭제",
            description = "저장한 헤어스타일의 하트를 다시 눌러 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 헤어스타일을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("hair/delete/{hairId}")
    public ResponseEntity<String> deleteSavedHair(
            @AuthUser AuthUserInfo authUserInfo,
            @Parameter(description = "삭제할 헤어스타일 ID", required = true)
            @PathVariable Long hairId
    ) {
        hairService.deleteSavedHair(authUserInfo.userId(), hairId);
        return ResponseEntity.ok("삭제되었습니다.");
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
}

