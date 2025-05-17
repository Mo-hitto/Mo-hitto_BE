package com.school.mohitto.security.resolver;

import com.school.mohitto.domain.authentication.AuthUserInfo;
import com.school.mohitto.domain.authentication.PrivateClaims;
import com.school.mohitto.domain.authentication.TokenProcessor;
import com.school.mohitto.domain.authentication.TokenType;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.security.annotation.AuthUser;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthUserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProcessor tokenProcessor;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class) &&
                parameter.getParameterType() == AuthUserInfo.class;
    }

    @Override
    public Object resolveArgument(
            @NotNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NotNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        String accessToken = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (accessToken == null || accessToken.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_REQUIRED);
        }

        Claims claims = tokenProcessor.decode(TokenType.ACCESS, accessToken)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));
        PrivateClaims privateClaims = PrivateClaims.from(claims);

        return new AuthUserInfo(privateClaims.userId());
    }
}
