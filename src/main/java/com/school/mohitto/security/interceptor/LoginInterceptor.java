package com.school.mohitto.security.interceptor;

import com.school.mohitto.domain.authentication.TokenType;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.service.authentication.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final AuthenticationService authenticationService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        if (isPreflightRequest(request)) {
            return true;
        }

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (accessToken == null || accessToken.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_REQUIRED);
        }

        authenticationService.getValidPrivateClaims(TokenType.ACCESS, accessToken);

        return true;
    }

    private boolean isPreflightRequest(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name()) &&
                request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) != null;
    }
}
