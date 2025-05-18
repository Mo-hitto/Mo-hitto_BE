package com.school.mohitto.controller.authentication;

import com.school.mohitto.config.SwaggerConfig;
import com.school.mohitto.domain.authentication.dto.LoginResult;
import com.school.mohitto.domain.authentication.dto.LoginWithAuthorizationCodeRequest;
import com.school.mohitto.domain.authentication.dto.LogoutRequest;
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
}
