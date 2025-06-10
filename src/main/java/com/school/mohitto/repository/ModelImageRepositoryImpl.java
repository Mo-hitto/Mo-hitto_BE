package com.school.mohitto.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.ModelImage;
import com.school.mohitto.domain.enums.HairLengthType;
import com.school.mohitto.domain.enums.HairTypeEnum;
import com.school.mohitto.domain.enums.HasBangType;
import com.school.mohitto.domain.enums.SexType;
import lombok.RequiredArgsConstructor;

import static com.school.mohitto.domain.QModelImage.*;

@RequiredArgsConstructor
public class ModelImageRepositoryImpl implements ModelImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ModelImage findModelImageByDiagnosisFeature(Diagnosis diagnosis, String style) {

        SexType sex = diagnosis.getDiagnosisSex().getSex().getSex();
        HairLengthType hairLength = diagnosis.getDiagnosisHairLength().getHairLength().getHairLength();
        HasBangType hasBangType = (sex == SexType.MALE)
                ? HasBangType.NONE
                : diagnosis.getDiagnosisHasbangs().getHasBangs().getHasBangType();
        HairTypeEnum hairType = (sex == SexType.MALE)
                ? diagnosis.getDiagnosisHairType().getHairType().getType()
                : HairTypeEnum.NONE;

        return queryFactory.selectFrom(modelImage)
                .where(
                        modelImage.name.eq(style),
                        modelImage.hairLength.eq(hairLength),
                        modelImage.sex.eq(sex),
                        modelImage.hasBangType.eq(hasBangType),
                        modelImage.hairTypeEnum.eq(hairType)
                )
                .fetchFirst();
    }
}
