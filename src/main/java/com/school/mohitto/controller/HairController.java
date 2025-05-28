package com.school.mohitto.controller;

import com.school.mohitto.domain.authentication.AuthUserInfo;
import com.school.mohitto.dto.responseDTO.HairResponseListDTO;
import com.school.mohitto.exception.annotation.PageConstraint;
import com.school.mohitto.security.annotation.AuthUser;
import com.school.mohitto.service.HairService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hairs")
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
}

