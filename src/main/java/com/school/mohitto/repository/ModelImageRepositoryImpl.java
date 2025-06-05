package com.school.mohitto.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.ModelImage;
import lombok.RequiredArgsConstructor;

import static com.school.mohitto.domain.QModelImage.*;

@RequiredArgsConstructor
public class ModelImageRepositoryImpl implements ModelImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ModelImage findModelImageByDiagnosisFeature(Diagnosis diagnosis) {
        ModelImage image = queryFactory.selectFrom(modelImage)
                .where(
                        modelImage.hairLength.eq(diagnosis.getDiagnosisHairLength().getHairLength().getHairLength()),
                        modelImage.sex.eq(diagnosis.getDiagnosisSex().getSex().getSex()),
                        modelImage.hairTypeEnum.eq(diagnosis.getDiagnosisHairType().getHairType().getType()),
                        modelImage.hasBangType.eq(diagnosis.getDiagnosisHasbangs().getHasBangs().getHasBangType())
                )
                .fetchFirst();
        return image;
    }
}
