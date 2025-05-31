package com.school.mohitto.service;

import com.school.mohitto.aws.s3.S3Uploader;
import com.school.mohitto.config.WebClientFactory;
import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.Hair;
import com.school.mohitto.domain.ModelImage;
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
import com.school.mohitto.repository.HairRepository;
import com.school.mohitto.repository.ModelImageRepository;
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
    private final DiagnosisHasBangsRepository diagnosisHasBangsRepository;
    private final DiagnosisMoodRepository diagnosisMoodRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final UploadImageRepository uploadImageRepository;
    private final HairRepository hairRepository;
    private final ModelImageRepository modelImageRepository;

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
        log.info(diagnosisId.toString());

        String user_image_url = s3Uploader.upload(multipartFile, "face");
        UploadImage image = new UploadImage(diagnosis, user_image_url);
        uploadImageRepository.save(image);
        log.info(user_image_url.toString());


        String hair_length = diagnosisHairLengthRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_LENGTH_NOT_FOUND))
                .getHairLength().getHairLength().getValue();
        log.info(hair_length.toString());

        String hair_type = diagnosisHairTypeRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_TYPE_NOT_FOUND))
                .getHairType().getType().getValue();
        log.info(hair_type.toString());


        String dyed = "X";

        String forehead_shape = diagnosisForeheadShapeRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.FOREHEAD_SHAPE_NOT_FOUND))
                .getForeheadShape().getShape().getValue();
        log.info(forehead_shape.toString());

        String cheekbone = diagnosisCheekboneRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHECKBONE_SHAPE_NOT_FOUND))
                .getCheekbone().getShape().getValue();
        log.info(cheekbone.toString());

        List<String> mood = diagnosisMoodRepository.findAllById(diagnosisId)
                .stream().map(diagnosisMood -> {
                    log.info(diagnosisMood.toString());
            return diagnosisMood.getMood().getMoodType().getValue();
        }).toList();


        String difficulty = diagnosisHairDifficultyRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIFFICULTY_NOT_FOUND))
                .getHairDifficulty().getType().getValue();
        log.info(difficulty.toString());

        String sex = diagnosisSexRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.GENDER_NOT_FOUND))
                .getSex().getSex().getValue();
        log.info(sex.toString());


        String has_bangs = diagnosisHasBangsRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST))
                .getHasBangs().getHasBangType().getValue();

        FaceExtractRequest faceExtractRequest =
                FaceExtractRequest.builder()
                        .user_image_url(user_image_url)
                        .hair_length(hair_length)
                        .hair_type(hair_type)
                        .dyed(dyed)
                        .forehead_shape(forehead_shape)
                        .cheekbone(cheekbone)
                        .mood(mood)
                        .difficulty(difficulty)
                        .sex(sex)
                        .has_bangs(has_bangs)
                        .build();

        FaceExtractResponse faceExtractResponse = extractFace(faceExtractRequest);
        String moods = String.join(",", mood);
        RecommandRequest recommandRequest =
                RecommandRequest.builder()
                        .hair_length(hair_length)
                        .hair_type(hair_type)
                        .sex(sex)
                        .cheekbone(cheekbone)
                        .mood(moods)
                        .forehead_shape(forehead_shape)
                        .difficulty(difficulty)
                        .has_bangs(has_bangs)
                        .face_shape_eval(faceExtractResponse.result().faceshape_eval())
                        .final_evaluation(faceExtractResponse.result().final_evaluation())
                        .build();
        log.info(recommandRequest.toString());

        return generateHairStyle(recommandRequest, diagnosisId);
    }


    private FaceExtractResponse extractFace(FaceExtractRequest faceExtractRequest) {
        log.info(faceExtractRequest.toString());
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
        log.info("CV 모델 완료");
        log.info(firstModelResponse.toString());
        return firstModelResponse;
    }

    private RecommandResponse generateHairStyle(RecommandRequest recommandRequest, Long diagnosisId) {
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

        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId).orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSIS_NOT_FOUND));
        response.recommendations().stream().map(
                recommendation ->
                {
                    // TO-DO : ModelImage modelImage = modelImageRepository.findBy
                    Hair hair = Hair.builder()
                            .name(recommendation.style())
                            .explanation(recommendation.description())
                            .isLiked(false)
                            .diagnosis(diagnosis)
                            .modelImage(null)
                            .build();

                    hairRepository.save(hair);
                    return null;
                }
        );

        return response;
    }
}

