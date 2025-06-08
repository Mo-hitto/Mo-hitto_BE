package com.school.mohitto.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.school.mohitto.domain.Diagnosis;
import com.school.mohitto.domain.ModelImage;
import com.school.mohitto.domain.enums.HairTypeEnum;
import com.school.mohitto.domain.enums.SexType;
import lombok.RequiredArgsConstructor;

import static com.school.mohitto.domain.QModelImage.*;

@RequiredArgsConstructor
public class ModelImageRepositoryImpl implements ModelImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ModelImage findModelImageByDiagnosisFeature(Diagnosis diagnosis, String style) {

        BooleanBuilder hairTypeCondition = new BooleanBuilder();

        if (diagnosis.getDiagnosisSex().getSex().getSex() == SexType.MALE) {
            hairTypeCondition.and(modelImage.hairTypeEnum.eq(diagnosis.getDiagnosisHairType().getHairType().getType()));
        } else {
            hairTypeCondition.and(modelImage.hairTypeEnum.eq(HairTypeEnum.NONE));
        }

        ModelImage image = queryFactory.selectFrom(modelImage)
                .where(
                        modelImage.name.eq(style),
                        modelImage.hairLength.eq(diagnosis.getDiagnosisHairLength().getHairLength().getHairLength()),
                        modelImage.sex.eq(diagnosis.getDiagnosisSex().getSex().getSex()),
                        modelImage.hasBangType.eq(diagnosis.getDiagnosisHasbangs().getHasBangs().getHasBangType()),
                        hairTypeCondition
                )
                .fetchFirst();
        return image;
    }
}
