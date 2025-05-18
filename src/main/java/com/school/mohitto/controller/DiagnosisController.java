package com.school.mohitto.controller;

import com.school.mohitto.config.response.GlobalResponse;
import com.school.mohitto.domain.authentication.AuthUserInfo;
import com.school.mohitto.dto.requestDTO.BasicDiagnosisRequest;
import com.school.mohitto.dto.requestDTO.PreferenceDiagnosisRequest;
import com.school.mohitto.dto.responseDTO.DiagnosisCreateResponse;
import com.school.mohitto.dto.responseDTO.DiagnosisPreferenceResponse;
import com.school.mohitto.dto.responseDTO.UploadImageResponse;
import com.school.mohitto.exception.dto.ErrorResponse;
import com.school.mohitto.security.annotation.AuthUser;
import com.school.mohitto.service.DiagnosisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/diagnosis")
@RequiredArgsConstructor
@Tag(name = "Diagnosis", description = "헤어 스타일 진단 관련 API")
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    @PostMapping("/basic")
    @Operation(
            summary = "기초 진단 등록",
            description = "성별, 모발 형태, 기장, 이마, 광대 정보를 입력받아 진단 ID를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "진단 정보 저장 성공",
                            content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
                    @ApiResponse(responseCode = "400", description = "입력 값 오류", content = @Content)
            }
    )
    public DiagnosisCreateResponse saveBasicDiagnosis(
            @Parameter(description = "Bearer {accessToken}", required = true)
            @AuthUser final AuthUserInfo authUserInfo,
            @RequestBody @Valid BasicDiagnosisRequest request) {
        DiagnosisCreateResponse diagnosisCreateResponse = diagnosisService.saveBasicDiagnosis(request, authUserInfo.userId());
        return diagnosisCreateResponse;
    }

    @PostMapping("/{diagnosisId}/preference")
    @Operation(
            summary = "선호도 진단 저장",
            description = "선택한 인상과 손질 난이도 정보를 진단에 연동하여 저장합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "진단 정보 저장 성공",
                            content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
                    @ApiResponse(responseCode = "400", description = "입력 값 오류",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "진단 ID 또는 선택값이 존재하지 않음",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public DiagnosisPreferenceResponse savePreferenceDiagnosis(
            @PathVariable Long diagnosisId,
            @RequestBody @Valid PreferenceDiagnosisRequest request
    ) {
        DiagnosisPreferenceResponse diagnosisPreferenceResponse = diagnosisService.savePreferenceDiagnosis(diagnosisId, request);
        return diagnosisPreferenceResponse;
    }



}
