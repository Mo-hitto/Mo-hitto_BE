package com.school.mohitto.service;

import com.school.mohitto.aws.s3.S3Uploader;
import com.school.mohitto.config.WebClientFactory;
import com.school.mohitto.domain.*;
import com.school.mohitto.dto.requestDTO.*;
import com.school.mohitto.dto.responseDTO.*;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.fastapi.FastapiProperties;
import com.school.mohitto.repository.*;
import com.school.mohitto.repository.DiagnosisRepositorys.*;
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
import java.time.Duration;
import java.util.List;
import java.util.Optional;
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
    private final CreatedImageRepository createdImageRepository;

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

        String hair_length = diagnosisHairLengthRepository.findByDiagnosisId(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_LENGTH_NOT_FOUND))
                .getHairLength().getHairLength().getValue();
        log.info(hair_length.toString());

        String hair_type = diagnosisHairTypeRepository.findByDiagnosisId(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_TYPE_NOT_FOUND))
                .getHairType().getType().getValue();
        log.info(hair_type.toString());


        String dyed = "X";

        String forehead_shape = diagnosisForeheadShapeRepository.findByDiagnosisId(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.FOREHEAD_SHAPE_NOT_FOUND))
                .getForeheadShape().getShape().getValue();
        log.info(forehead_shape.toString());

        String cheekbone = diagnosisCheekboneRepository.findByDiagnosisId(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHECKBONE_SHAPE_NOT_FOUND))
                .getCheekbone().getShape().getValue();
        log.info(cheekbone.toString());

        List<String> mood = diagnosisMoodRepository.findAllByDiagnosisId(diagnosisId)
                .stream().map(diagnosisMood -> {
                    log.info(diagnosisMood.toString());
            return diagnosisMood.getMood().getMoodType().getValue();
        }).toList();


        String difficulty = diagnosisHairDifficultyRepository.findByDiagnosisId(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIFFICULTY_NOT_FOUND))
                .getHairDifficulty().getType().getValue();
        log.info(difficulty.toString());

        String sex = diagnosisSexRepository.findByDiagnosisId(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.GENDER_NOT_FOUND))
                .getSex().getSex().getValue();
        log.info(sex.toString());


        String has_bangs = diagnosisHasBangsRepository.findByDiagnosisId(diagnosisId)
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
                    ModelImage modelImage = modelImageRepository.findModelImageByDiagnosisFeature(diagnosis,recommendation.style());
                    Hair hair = Hair.builder()
                            .name(recommendation.style())
                            .explanation(recommendation.description())
                            .isLiked(false)
                            .diagnosis(diagnosis)
                            .modelImage(modelImage)
                            .build();

                    Long hairId = hairRepository.save(hair).getId();
                    return new FinalRecommandResponse.StyleHairInfo(
                            hairId,
                            recommendation.style(),
                            modelImage != null ? modelImage.getId() : null,
                            recommendation.description(),
                            recommendation.hair_shops());
                }
        ).collect(Collectors.toList());
        log.info("Rag 모델 완료");
        return new FinalRecommandResponse(result);
    }

    @Transactional
    public ChangeFaceSimulationResponse changeFaceInRecommandService(
            ChangeFaceRecommandRequest inputFaceRequest ) throws IOException {

        Hair hair = hairRepository.findById(inputFaceRequest.hairId()).orElseThrow(() -> new CustomException(ErrorCode.HAIR_NOT_FOUND));

        String user_image_url = hair.getDiagnosis().getUploadImage().getUploadImageUrl();
        log.info("추천 헤어 적용 전 이미지 사진: " + user_image_url);

        ModelImage modelImage = modelImageRepository.findById(inputFaceRequest.modelImageId())
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_NOT_FOUND));

        ChangeFaceRequest changeFaceRequest = new ChangeFaceRequest(user_image_url, modelImage.getUploadImageUrl());

        Resource response = Mono.delay(Duration.ofSeconds(10)) // 10초 대기
                .then(
                        webClientFactory.create(fastapiProperties.hairTransfer())
                                .post()
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
                )
                .block(); // 전체 실행
        log.info("시뮬레이션 모델 완료");

        MultipartFile file = convertResourceToMultipartFile(response);
        String result_image_url = s3Uploader.upload(file, "result");
        log.info("추천 헤어 시뮬레이션 결과 이미지: " + result_image_url);

        createdImageRepository.save(new CreatedImage(hair,result_image_url));

        return new ChangeFaceSimulationResponse(result_image_url);
    }

    public ChangeFaceSimulationResponse changeFaceInSimulationService(
            MultipartFile multipartFile,
            ChangeFaceSimulationRequest inputFaceRequest) throws IOException {
        String user_image_url = s3Uploader.upload(multipartFile, "face");
        log.info(user_image_url);

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
        log.info("시뮬레이션 완료");

        MultipartFile file = convertResourceToMultipartFile(response);
        String result_image_url = s3Uploader.upload(file, "result");
        log.info("시뮬레이션 서비스에서의 이미지 결과 : " + result_image_url);
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

    @Transactional
    public LikeToggleResponse toggleLike(Long hairId, String imageUrl) {
        Optional<CreatedImage> existing = createdImageRepository.findByHairIdAndImageUrl(hairId, imageUrl);

        if (existing.isPresent()) {
            createdImageRepository.delete(existing.get());
            return new LikeToggleResponse(hairId, imageUrl, false); // 좋아요 취소
        } else {
            Hair hair = hairRepository.findById(hairId)
                    .orElseThrow(() -> new CustomException(ErrorCode.HAIR_NOT_FOUND));

            CreatedImage createdImage = CreatedImage.builder()
                    .hair(hair)
                    .createdImageUrl(imageUrl)
                    .build();

            hair.setCreatedImage(createdImage);

            createdImageRepository.save(createdImage);
            return new LikeToggleResponse(hairId, imageUrl, true); // 좋아요 등록
        }
    }

}

