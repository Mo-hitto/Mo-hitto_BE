package com.school.mohitto.service;

import com.school.mohitto.Image.FileUploader;
import com.school.mohitto.domain.*;
import com.school.mohitto.domain.mapping.*;
import com.school.mohitto.dto.requestDTO.BasicDiagnosisRequest;
import com.school.mohitto.dto.requestDTO.PreferenceDiagnosisRequest;
import com.school.mohitto.dto.responseDTO.DiagnosisCreateResponse;
import com.school.mohitto.dto.responseDTO.DiagnosisPreferenceResponse;
import com.school.mohitto.dto.responseDTO.UploadImageResponse;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.repository.*;
import com.school.mohitto.repository.DiagnosisRepositorys.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final GenderRepository genderRepository;
    private final HairTypeRepository hairTypeRepository;
    private final HairLengthRepository hairLengthRepository;
    private final ForeheadShapeRepository foreheadShapeRepository;
    private final CheckboneShapeRepository checkboneShapeRepository;
    private final HairDifficultyRepository hairDifficultyRepository;
    private final ImpressionRepository impressionRepository;
    private final UserRepository userRepository;


    private final DiagnosisGenderRepository diagnosisGenderRepository;
    private final DiagnosisHairTypeRepository diagnosisHairTypeRepository;
    private final DiagnosisHairLengthRepository diagnosisHairLengthRepository;
    private final DiagnosisForeheadShapeRepository diagnosisForeheadShapeRepository;
    private final DiagnosisCheckboneShapeRepository diagnosisCheckboneShapeRepository;
    private final DiagnosisHairDifficultyRepository diagnosisHairDifficultyRepository;
    private final DiagnosisImpressionRepository diagnosisImpressionRepository;


    @Transactional
    public DiagnosisCreateResponse saveBasicDiagnosis(BasicDiagnosisRequest dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        Diagnosis diagnosis = Diagnosis.create(user);
        diagnosisRepository.save(diagnosis);


        // 성별
        Gender gender = genderRepository.findById(dto.genderId())
                .orElseThrow(() -> new CustomException(ErrorCode.GENDER_NOT_FOUND));
        DiagnosisGender dg = DiagnosisGender.of(diagnosis, gender);
        diagnosis.addDiagnosisGender(dg);
        diagnosisGenderRepository.save(dg);

        // 모발 형태
        HairType hairType = hairTypeRepository.findById(dto.hairTypeId())
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_TYPE_NOT_FOUND));
        DiagnosisHairType dht = DiagnosisHairType.of(diagnosis, hairType);
        diagnosis.addDiagnosisHairType(dht);
        diagnosisHairTypeRepository.save(dht);

        // 모발 길이
        HairLength hairLength = hairLengthRepository.findById(dto.hairLengthId())
                .orElseThrow(() -> new CustomException(ErrorCode.HAIR_LENGTH_NOT_FOUND));
        DiagnosisHairLength dhl = DiagnosisHairLength.of(diagnosis, hairLength);
        diagnosis.addDiagnosisHairLength(dhl);
        diagnosisHairLengthRepository.save(dhl);

        // 이마 형태
        ForeheadShape foreheadShape = foreheadShapeRepository.findById(dto.foreheadShapeId())
                .orElseThrow(() -> new CustomException(ErrorCode.FOREHEAD_SHAPE_NOT_FOUND));
        DiagnosisForeheadShape dfs = DiagnosisForeheadShape.of(diagnosis, foreheadShape);
        diagnosis.addDiagnosisForeheadShape(dfs);
        diagnosisForeheadShapeRepository.save(dfs);

        // 광대 형태
        CheckboneShape checkboneShape = checkboneShapeRepository.findById(dto.checkboneShapeId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHECKBONE_SHAPE_NOT_FOUND));
        DiagnosisCheckboneShape dcs = DiagnosisCheckboneShape.of(diagnosis, checkboneShape);
        diagnosis.addDiagnosisCheckboneShape(dcs);
        diagnosisCheckboneShapeRepository.save(dcs);

        return DiagnosisCreateResponse.from(diagnosis.getId());

    }

    @Transactional
    public DiagnosisPreferenceResponse savePreferenceDiagnosis(Long diagnosisId, PreferenceDiagnosisRequest request) {
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSIS_NOT_FOUND));

        int count = 0;

        for (Long impressionId : request.impressionIds()) {
            Impression impression = impressionRepository.findById(impressionId)
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER));
            DiagnosisImpression di = DiagnosisImpression.of(diagnosis, impression);
            diagnosis.addDiagnosisImpression(di);
            diagnosisImpressionRepository.save(di);
            count++;
        }

        HairDifficulty difficulty = hairDifficultyRepository.findById(request.difficultyId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER));
        DiagnosisHairDifficulty dhd = DiagnosisHairDifficulty.of(diagnosis, difficulty);
        diagnosis.addDiagnosisHairDifficulty(dhd);
        diagnosisHairDifficultyRepository.save(dhd);

        return DiagnosisPreferenceResponse.of(diagnosisId, count);
    }

}
