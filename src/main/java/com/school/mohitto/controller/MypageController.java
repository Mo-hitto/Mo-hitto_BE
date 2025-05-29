package com.school.mohitto.controller;

import com.school.mohitto.domain.authentication.AuthUserInfo;
import com.school.mohitto.dto.responseDTO.SalonResponseDTO;
import com.school.mohitto.security.annotation.AuthUser;
import com.school.mohitto.service.SalonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "myPage", description = "마이페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final SalonService salonService;

    @GetMapping("/mypage/salons")
    @Operation(summary = "내가 저장한 미용실 목록 조회")
    public ResponseEntity<List<SalonResponseDTO>> getMySavedSalons(
            @Parameter(hidden = true) @AuthUser AuthUserInfo authUserInfo
    ) {
        List<SalonResponseDTO> salons = salonService.getSavedSalons(authUserInfo.userId());
        return ResponseEntity.ok(salons);
    }


    @DeleteMapping("/{salonId}")
    @Operation(summary = "저장한 미용실 삭제", description = "저장된 미용실을 하트를 다시 눌러 삭제합니다.")
    public ResponseEntity<String> deleteSavedSalon(
            @Parameter(hidden = true) @AuthUser AuthUserInfo authUserInfo,
            @Parameter(description = "삭제할 미용실 ID") @PathVariable Long salonId
    ) {
        salonService.deleteSavedSalon(authUserInfo.userId(), salonId);
        return ResponseEntity.ok("삭제되었습니다.");
    }

}
