package com.school.mohitto.controller;

import com.school.mohitto.domain.authentication.AuthUserInfo;
import com.school.mohitto.dto.responseDTO.SalonInformationResponse;
import com.school.mohitto.security.annotation.AuthUser;
import com.school.mohitto.service.SalonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Salon", description = "미용실 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/salon")
public class SalonController {

    private final SalonService salonService;

    /**
     * 네이버 검색 API를 이용하여 동적으로 검색어를 지정하여 장소를 검색합니다.
     *
     * @param query 동적으로 지정된 검색어
     * @return 검색된 장소 목록
     */
    @GetMapping("/naver")
    public SalonInformationResponse naverSearchDynamic(@RequestParam String query) {
        return salonService.searchSalon(query);
    }

    @PostMapping("/save")
    @Operation(summary = "미용실 저장")
    public ResponseEntity<String> saveUserSalon(
            @AuthUser AuthUserInfo authUserInfo,
            @RequestParam String address  // 또는 salonId
    ) {
        salonService.saveUserSalon(authUserInfo.userId(), address);
        return ResponseEntity.ok("미용실이 저장되었습니다.");
    }




}
