package com.school.mohitto.controller.authentication;

import com.school.mohitto.config.SwaggerConfig;
import com.school.mohitto.domain.authentication.TokenType;
import com.school.mohitto.domain.authentication.dto.*;
import com.school.mohitto.service.authentication.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "인증 API")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/{oauth2Type}")
    @Operation(
            summary = "OAuth 2.0 로그인 페이지로 redirect",
            description = """
                     OAuth 2.0 로그인 페이지로 redirect합니다.\n
                     로그인 이후 redirect 되는 url의 `code` query parameter에 Authorization code가 포함되어 있습니다.
                     """
    )
    @ApiResponse(responseCode = "302", description = "OAuth 2.0 로그인 페이지로 redirect 성공")
    public ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @Parameter(description = "OAuth 2.0 Type (대소문자 상관 없음)", example = "KAKAO")
            @PathVariable String oauth2Type
    ) {
        final String redirectUrl = authenticationService.getAuthCodeRequestUrl(oauth2Type);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam("code") String code) {
        /*LoginResult loginResult = authenticationService.loginWithAuthorizationCode(
                "KAKAO", code, LocalDateTime.now());*/

        return ResponseEntity.ok(code);
    }

    @PostMapping("/login/{oauth2Type}")
    @Operation(
            summary = "OAuth 2.0 로그인",
            description = "OAuth 2.0 Authorization code를 통해 로그인합니다."
    )
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    public LoginResult loginWithAuthorizationCode(
            @Parameter(description = "OAuth 2.0 Type (대소문자 상관 없음)", example = "KAKAO")
            @PathVariable String oauth2Type,
            @RequestBody @Valid LoginWithAuthorizationCodeRequest request
    ) {
        LoginResult loginResult = authenticationService.loginWithAuthorizationCode(
                oauth2Type,
                request.authorizationCode(),
                LocalDateTime.now()
        );

        return loginResult;
    }

    @PostMapping("/logout")
    @Operation(
            summary = "로그아웃",
            description = "로그아웃합니다.",
            security = @SecurityRequirement(name = SwaggerConfig.SERVICE_SECURITY_SCHEME_NAME)
    )
    @ApiResponse(responseCode = "204", description = "로그아웃 성공")
    public ResponseEntity<Void> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestBody @Valid LogoutRequest request
    ) {


        authenticationService.logout(accessToken, request.refreshToken());

        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/validate-token")
    @Operation(
            summary = "Access Token 유효성 검사",
            description = "Access Token의 유효성을 검사합니다.",
            security = @SecurityRequirement(name = SwaggerConfig.SERVICE_SECURITY_SCHEME_NAME)
    )
    @ApiResponse(responseCode = "200", description = "Access Token 유효성 검사 성공")
    public ValidateTokenResponse validateToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken
    ) {
        boolean validated = authenticationService.isValidToken(TokenType.ACCESS, accessToken);
        ValidateTokenResponse validateTokenResponse = new ValidateTokenResponse(validated);

        return validateTokenResponse;
    }

    @PostMapping("/refresh-token")
    @Operation(
            summary = "새 토큰 발급",
            description = "Refresh Token으로 새로운 Access Token과 Refresh Token을 발급합니다."
    )
    @ApiResponse(responseCode = "200", description = "새 토큰 발급 성공")
    public ResponseEntity<TokenRefreshResponse> refreshToken(
            @RequestBody @Valid TokenRefreshRequest request
    ) {
        TokenRefreshResult result = authenticationService.refreshToken(LocalDateTime.now(), request.refreshToken());
        TokenRefreshResponse response = TokenRefreshResponse.from(result);

        return ResponseEntity.ok(response);
    }
}
