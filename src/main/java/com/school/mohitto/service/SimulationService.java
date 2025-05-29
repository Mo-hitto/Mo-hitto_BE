package com.school.mohitto.service;

import com.school.mohitto.config.WebClientFactory;
import com.school.mohitto.dto.requestDTO.FaceExtractRequest;
import com.school.mohitto.dto.responseDTO.FaceExtractResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SimulationService {

    private final WebClientFactory webClientFactory;

    private static String EXTRACT_FACE_URL = "Dockerfile.linkUrl:8000";



    private FaceExtractResponse extractFace(FaceExtractRequest faceExtractRequest) {
        Map<String, Object> payload = buildFirstModelPayload(faceExtractRequest);

        FaceExtractResponse firstModelResponse = webClientFactory.create(EXTRACT_FACE_URL).post()
                .uri("/run-extract/")
                .bodyValue(payload)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(error ->
                                Mono.error(new RuntimeException("CV 모델 오류: " + error))
                        )
                )
                .bodyToMono(FaceExtractResponse.class)
                .block();

        if (firstModelResponse == null) {
            throw new RuntimeException("추천 응답이 비어있습니다.");
        }

        return firstModelResponse;
    }

    private Map<String, Object> buildFirstModelPayload(FaceExtractRequest request) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_image_url", request.user_image_url());
        payload.put("hair_length", request.hair_length());
        payload.put("hair_type", request.hair_type());
        payload.put("dyed",request.dyed());
        payload.put("forehead_shape", request.forehead_shape());
        payload.put("cheekbone", request.cheekbone());
        payload.put("mood", request.mood());
        payload.put("difficulty", request.difficulty());
        payload.put("sex", request.sex());
        return payload;
    }

}

