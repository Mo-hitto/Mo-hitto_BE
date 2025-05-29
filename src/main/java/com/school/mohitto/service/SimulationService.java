package com.school.mohitto.service;

import com.school.mohitto.aws.s3.S3Uploader;
import com.school.mohitto.config.WebClientFactory;
import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.UploadImage;
import com.school.mohitto.dto.requestDTO.FaceExtractRequest;
import com.school.mohitto.dto.requestDTO.RecommandRequest;
import com.school.mohitto.dto.requestDTO.SimulationRequest;
import com.school.mohitto.dto.responseDTO.FaceExtractResponse;
import com.school.mohitto.dto.responseDTO.RecommandResponse;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.fastapi.FastapiProperties;
import com.school.mohitto.repository.DiagnosisRepository;
import com.school.mohitto.repository.DiagnosisRepositorys.*;
import com.school.mohitto.repository.UploadImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SimulationService {

    private final DiagnosisSexRepository diagnosisSexRepository;
    private final DiagnosisHairTypeRepository diagnosisHairTypeRepository;
    private final DiagnosisHairLengthRepository diagnosisHairLengthRepository;
    private final DiagnosisForeheadShapeRepository diagnosisForeheadShapeRepository;
    private final DiagnosisCheekboneRepository diagnosisCheekboneRepository;
    private final DiagnosisHairDifficultyRepository diagnosisHairDifficultyRepository;
    private final DiagnosisMoodRepository diagnosisMoodRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final UploadImageRepository uploadImageRepository;

    private final WebClientFactory webClientFactory;
    private final S3Uploader s3Uploader;
    private final FastapiProperties fastapiProperties;

    @Transactional
    public RecommandResponse extractFaceAndRecommand(
            MultipartFile multipartFile,
            SimulationRequest simulationRequest ) throws IOException {

        Long diagnosisId = simulationRequest.diagnosisId();
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSIS_NOT_FOUND));

        String user_image_url = s3Uploader.upload(multipartFile, "face");
        UploadImage image = new UploadImage(diagnosis, user_image_url);
        uploadImageRepository.save(image);

        String hair_length = diagnosisHairLengthRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_LENGTH_NOT_FOUND))
                .getHairLength().getHairLength().getValue();

        String hair_type = diagnosisHairTypeRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_TYPE_NOT_FOUND))
                .getHairType().getType().getValue();

        String dyed = "X";

        String forehead_shape = diagnosisForeheadShapeRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.FOREHEAD_SHAPE_NOT_FOUND))
                .getForeheadShape().getShape().getValue();

        String cheekbone = diagnosisCheekboneRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHECKBONE_SHAPE_NOT_FOUND))
                .getCheekbone().getShape().getValue();

        List<String> mood = diagnosisMoodRepository.findAllById(diagnosisId)
                .stream().map(diagnosisMood -> {
            return diagnosisMood.getMood().getMoodType().getValue();
        }).toList();

        String difficulty = diagnosisHairDifficultyRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIFFICULTY_NOT_FOUND))
                .getHairDifficulty().getType().getValue();

        String sex = diagnosisSexRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.GENDER_NOT_FOUND))
                .getSex().getSex().name();

        FaceExtractRequest faceExtractRequest =
                new FaceExtractRequest(user_image_url,hair_length,hair_type,dyed,forehead_shape,cheekbone,mood,difficulty,sex);

        FaceExtractResponse faceExtractResponse = extractFace(faceExtractRequest);

        RecommandRequest recommandRequest =
                new RecommandRequest(user_image_url,hair_length,sex,cheekbone,mood,forehead_shape,difficulty,faceExtractResponse.faceshape_eval(),faceExtractResponse.final_evaluation());

        return generateHairStyle(recommandRequest);
    }


    private FaceExtractResponse extractFace(FaceExtractRequest faceExtractRequest) {
        FaceExtractResponse firstModelResponse = webClientFactory.create(fastapiProperties.extractFace()).post()
                .uri("/run-extract/")
                .bodyValue(faceExtractRequest)
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

    private RecommandResponse generateHairStyle(RecommandRequest recommandRequest) {
        RecommandResponse response = webClientFactory.create(fastapiProperties.graphRag()).post()
                .uri("/recommend")
                .bodyValue(recommandRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(error ->
                                Mono.error(new RuntimeException("GraphRAG 오류: " + error))
                        )
                )
                .bodyToMono(RecommandResponse.class)
                .block();

        if (response == null) {
            throw new RuntimeException("추천 응답이 비어있습니다.");
        }
        return response;
    }
}

