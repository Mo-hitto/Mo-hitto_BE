package com.school.mohitto.controller;

import com.school.mohitto.dto.responseDTO.SalonInformationResponse;
import com.school.mohitto.service.SalonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
}
