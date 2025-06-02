package com.school.mohitto.service;

import com.school.mohitto.aws.s3.S3Uploader;
import com.school.mohitto.config.WebClientFactory;
import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.ModelImage;
import com.school.mohitto.domain.Hair;
import com.school.mohitto.domain.UploadImage;
import com.school.mohitto.dto.requestDTO.*;
import com.school.mohitto.dto.responseDTO.ChangeFaceSimulationResponse;
import com.school.mohitto.dto.responseDTO.FaceExtractResponse;
import com.school.mohitto.dto.responseDTO.FinalRecommandResponse;
import com.school.mohitto.dto.responseDTO.RecommandResponse;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.fastapi.FastapiProperties;
import com.school.mohitto.repository.DiagnosisRepository;
import com.school.mohitto.repository.DiagnosisRepositorys.*;
import com.school.mohitto.repository.ModelImageRepository;
import com.school.mohitto.repository.HairRepository;
import com.school.mohitto.repository.UploadImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

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
    private final ModelImageRepository modelImageRepository;

    private final HairRepository hairRepository;

    private final WebClientFactory webClientFactory;
    private final S3Uploader s3Uploader;
    private final FastapiProperties fastapiProperties;

    @Transactional
    public FinalRecommandResponse extractFaceAndRecommand(
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

    private FinalRecommandResponse generateHairStyle(RecommandRequest recommandRequest, Long diagnosisId) {
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
        List<FinalRecommandResponse.StyleHairInfo> result = response.recommendations().stream().map(
                recommendation ->
                {
                    ModelImage modelImage = modelImageRepository.findModelImageByDiagnosisFeature(diagnosis);
                    Hair hair = Hair.builder()
                            .name(recommendation.style())
                            .explanation(recommendation.description())
                            .isLiked(false)
                            .diagnosis(diagnosis)
                            .modelImage(modelImage)
                            .build();

                    hairRepository.save(hair);
                    return new FinalRecommandResponse.StyleHairInfo(
                            recommendation.style(),
                            modelImage.getId(),
                            recommendation.description(),
                            recommendation.hair_shops());
                }
        ).collect(Collectors.toList());

        return new FinalRecommandResponse(result);
    }

    public ChangeFaceSimulationResponse changeFaceInSimulationService(
            MultipartFile multipartFile,
            ChangeFaceSimulationRequest inputFaceRequest) throws IOException {
        String user_image_url = s3Uploader.upload(multipartFile, "face");

        ModelImage modelImage = modelImageRepository.findById(inputFaceRequest.modelImageId())
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_NOT_FOUND));

        ChangeFaceRequest changeFaceRequest = new ChangeFaceRequest(user_image_url, modelImage.getUploadImageUrl());

        Resource response = webClientFactory.create(fastapiProperties.hairTransfer()).post()
                .uri("/simulate")
                .accept(MediaType.IMAGE_PNG)
                .accept(MediaType.IMAGE_JPEG)
                .bodyValue(changeFaceRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(error ->
                                Mono.error(new RuntimeException("시뮬레이션 모델 오류: " + error))
                        )
                )
                .bodyToMono(Resource.class)
                .block();

        MultipartFile file = convertResourceToMultipartFile(response);
        String result_image_url = s3Uploader.upload(file, "result");
        return new ChangeFaceSimulationResponse(result_image_url);
    }

    private MultipartFile convertResourceToMultipartFile(Resource resource) throws IOException {
        String originalFileName = resource.getFilename();  // 예: "123_result.png"
        String contentType = "image/jpg"; // 필요시 확장자 따라 변경 가능

        try (InputStream inputStream = resource.getInputStream()) {
            return new MockMultipartFile(
                    "file",                  // field name (multipart 필드명)
                    originalFileName,       // 클라이언트에서 올라온 파일명
                    contentType,            // Content-Type
                    inputStream              // 파일 데이터
            );
        }
    }
}

