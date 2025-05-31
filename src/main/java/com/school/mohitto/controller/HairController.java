package com.school.mohitto.controller;

import com.school.mohitto.domain.authentication.AuthUserInfo;
import com.school.mohitto.dto.responseDTO.HairLikeToggleResponse;
import com.school.mohitto.security.annotation.AuthUser;
import com.school.mohitto.service.HairService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hairs")
@Tag(name = "Hair", description = "헤어 추천 및 좋아요 API")
public class HairController {

    private final HairService hairService;

    @Operation(summary = "헤어 좋아요 토글", description = "해당 헤어 ID의 좋아요 상태를 반전시키고, 좋아요된 헤어 ID를 반환합니다.")
    @PatchMapping("/{hairId}/like/toggle")
    public HairLikeToggleResponse toggleLike(
            @AuthUser final AuthUserInfo authUserInfo,
            @Parameter(description = "좋아요 토글할 헤어 ID", required = true)
            @PathVariable Long hairId) {
        return hairService.toggleLikeStatus(hairId);
    }

}
