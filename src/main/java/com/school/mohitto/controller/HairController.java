package com.school.mohitto.controller;

import com.school.mohitto.domain.authentication.AuthUserInfo;
import com.school.mohitto.dto.responseDTO.HairResponseListDTO;
import com.school.mohitto.exception.annotation.PageConstraint;
import com.school.mohitto.security.annotation.AuthUser;
import com.school.mohitto.service.HairService;
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
@RequestMapping("/hairs")
@Tag(name = "Hair", description = "헤어스타일 저장/조회 API")
public class HairController {

    private final HairService hairService;

    @Operation(summary = "저장한 헤어스타일 목록 조회")
    @GetMapping
    public ResponseEntity<HairResponseListDTO> getSavedHairs(
            @Parameter(description = "Bearer {accessToken}", required = true)
            @AuthUser AuthUserInfo authUserInfo,
            @ParameterObject
            @PageableDefault(size = 6, sort = "createdDate", direction = Sort.Direction.DESC)
            @Valid @PageConstraint Pageable pageable
    ) {
        HairResponseListDTO result = hairService.getSavedHairs(authUserInfo.userId(), pageable);
        return ResponseEntity.ok(result);
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
    @DeleteMapping("/{hairId}")
    public ResponseEntity<String> deleteSavedHair(
            @Parameter(description = "Bearer {accessToken}", required = true)
            @AuthUser AuthUserInfo authUserInfo,

            @Parameter(description = "삭제할 헤어스타일 ID", required = true)
            @PathVariable Long hairId
    ) {
        hairService.deleteSavedHair(authUserInfo.userId(), hairId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}

