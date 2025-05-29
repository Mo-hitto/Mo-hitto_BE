package com.school.mohitto.service;

import com.school.mohitto.domain.*;
import com.school.mohitto.domain.mapping.*;
import com.school.mohitto.dto.requestDTO.BasicDiagnosisRequest;
import com.school.mohitto.dto.requestDTO.PreferenceDiagnosisRequest;
import com.school.mohitto.dto.responseDTO.DiagnosisCreateResponse;
import com.school.mohitto.dto.responseDTO.DiagnosisPreferenceResponse;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.repository.*;
import com.school.mohitto.repository.DiagnosisRepositorys.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final SexRepository sexRepository;
    private final HairTypeRepository hairTypeRepository;
    private final HairLengthRepository hairLengthRepository;
    private final ForeheadShapeRepository foreheadShapeRepository;
    private final CheekboneRepository cheekboneRepository;
    private final HairDifficultyRepository hairDifficultyRepository;
    private final MoodRepository moodRepository;
    private final UserRepository userRepository;


    private final DiagnosisSexRepository diagnosisSexRepository;
    private final DiagnosisHairTypeRepository diagnosisHairTypeRepository;
    private final DiagnosisHairLengthRepository diagnosisHairLengthRepository;
    private final DiagnosisForeheadShapeRepository diagnosisForeheadShapeRepository;
    private final DiagnosisCheekboneRepository diagnosisCheekboneRepository;
    private final DiagnosisHairDifficultyRepository diagnosisHairDifficultyRepository;
    private final DiagnosisMoodRepository diagnosisMoodRepository;


    @Transactional
    public DiagnosisCreateResponse saveBasicDiagnosis(BasicDiagnosisRequest dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        Diagnosis diagnosis = Diagnosis.create(user);
        diagnosisRepository.save(diagnosis);


        // 성별
        Sex sex = sexRepository.findById(dto.sexId())
                .orElseThrow(() -> new CustomException(ErrorCode.GENDER_NOT_FOUND));
        DiagnosisSex dg = DiagnosisSex.of(diagnosis, sex);
        diagnosis.addDiagnosisGender(dg);
        diagnosisSexRepository.save(dg);

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
        Cheekbone cheekbone = cheekboneRepository.findById(dto.cheekboneId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHECKBONE_SHAPE_NOT_FOUND));
        DiagnosisCheekbone dcs = DiagnosisCheekbone.of(diagnosis, cheekbone);
        diagnosis.addDiagnosisCheckboneShape(dcs);
        diagnosisCheekboneRepository.save(dcs);

        return DiagnosisCreateResponse.from(diagnosis.getId());

    }

    @Transactional
    public DiagnosisPreferenceResponse savePreferenceDiagnosis(Long diagnosisId, PreferenceDiagnosisRequest request) {
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSIS_NOT_FOUND));

        int count = 0;

        for (Long impressionId : request.moodIds()) {
            Mood mood = moodRepository.findById(impressionId)
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER));
            DiagnosisMood di = DiagnosisMood.of(diagnosis, mood);
            diagnosis.addDiagnosisImpression(di);
            diagnosisMoodRepository.save(di);
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
