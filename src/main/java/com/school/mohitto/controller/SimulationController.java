package com.school.mohitto.controller;

import com.school.mohitto.dto.requestDTO.ChangeFaceSimulationRequest;
import com.school.mohitto.dto.requestDTO.SimulationRequest;
import com.school.mohitto.dto.responseDTO.ChangeFaceSimulationResponse;
import com.school.mohitto.dto.responseDTO.FinalRecommandResponse;
import com.school.mohitto.dto.responseDTO.LikeToggleResponse;
import com.school.mohitto.dto.responseDTO.RecommandResponse;
import com.school.mohitto.service.SimulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/simulation")
@RequiredArgsConstructor
@Tag(name = "Simulation", description = "AI 모델 관련 API")
public class SimulationController {

    private final SimulationService simulationService;

    @PostMapping(value = "/recommand" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FinalRecommandResponse getRecommand(
            @RequestPart(value = "image", required = true)
            MultipartFile multipartFile,
            @RequestPart(value = "data") SimulationRequest simulationRequest
    ) throws IOException {
        return simulationService.extractFaceAndRecommand(multipartFile,simulationRequest);
    }

    @PostMapping(value = "/transfer-face", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChangeFaceSimulationResponse changeFaceInSimulationService(
            @RequestPart(value = "image", required = true)
            MultipartFile multipartFile,
            @RequestPart(value = "data") ChangeFaceSimulationRequest modelImageId
    ) throws IOException {
        return simulationService.changeFaceInSimulationService(multipartFile, modelImageId);
    }

    @PostMapping("/like-toggle")
    @Operation(summary = "좋아요 토글 (등록 또는 취소)")
    public LikeToggleResponse toggleLike(
            @RequestParam Long hairId,
            @RequestParam String imageUrl
    ) {
        LikeToggleResponse response = simulationService.toggleLike(hairId, imageUrl);
        return response;
    }

}
