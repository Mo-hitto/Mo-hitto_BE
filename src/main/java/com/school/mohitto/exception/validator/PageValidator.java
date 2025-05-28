package com.school.mohitto.exception.validator;

import com.school.mohitto.exception.annotation.PageConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.data.domain.Pageable;

public class PageValidator implements ConstraintValidator<PageConstraint, Pageable> {

    @Override
    public boolean isValid(Pageable pageable, ConstraintValidatorContext context) {
        // null인 경우는 다른 @NotNull로 따로 처리
        if (pageable == null) return true;

        return pageable.getPageNumber() >= 0;
    }
}

