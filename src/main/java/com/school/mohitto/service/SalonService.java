package com.school.mohitto.service;

import com.school.mohitto.config.WebClientFactory;
import com.school.mohitto.domain.Salon;
import com.school.mohitto.dto.responseDTO.SalonInformationResponse;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.naver.NaverProperties;
import com.school.mohitto.naver.dto.LocalItem;
import com.school.mohitto.naver.dto.NaverLocalSearchResponse;
import com.school.mohitto.repository.SalonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SalonService {

    private final WebClientFactory webClientFactory;
    private final NaverProperties naverProperties;
    private final SalonRepository salonRepository;

    private static String NAVER_SEARCH_API_URL = "https://openapi.naver.com/v1/search/local";

    /**
     * 네이버 검색 API를 이용하여 장소를 검색하는 메서드입니다.
     * @param name 검색할 장소의 이름 또는 검색어
     * @return 검색된 장소 목록
     */
    @Transactional
    public SalonInformationResponse searchSalon(String name) {

        // 네이버 검색 API를 호출하기 위한 URI 생성
        NaverLocalSearchResponse response = webClientFactory.create(NAVER_SEARCH_API_URL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .queryParam("query", name)
                        .build(true)
                )
                .header("X-Naver-Client-Id", naverProperties.clientId())
                .header("X-Naver-Client-Secret", naverProperties.secret())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(NaverLocalSearchResponse.class)
                .block();
        if (response == null || response.items() == null || response.items().isEmpty()) {
            throw new CustomException(ErrorCode.NO_SEARCH_RESULT);
        }

        LocalItem localItem = response.items().get(0);
        Salon salon = salonRepository.findByAddress(localItem.address())
                .orElseGet(() -> salonRepository.save(
                        Salon.builder()
                                .name(localItem.title())
                                .address(localItem.address())
                                .link(localItem.link())
                                .description(localItem.description())
                                .telephone(localItem.telephone())
                                .build()
                ));
        return new SalonInformationResponse(
                localItem.title(),
                localItem.link(),
                localItem.description(),
                localItem.address(),
                localItem.telephone(),
                localItem.mapx(),
                localItem.mapy());
    }
}

