package com.school.mohitto.service.authentication;

import com.school.mohitto.domain.User;
import com.school.mohitto.domain.authentication.*;
import com.school.mohitto.domain.authentication.dto.TokenRefreshResult;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.domain.authentication.dto.LoginResult;
import com.school.mohitto.repository.BlackListTokenRepository;
import com.school.mohitto.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final OAuth2HandlerProvider oAuth2HandlerProvider;
    private final TokenProcessor tokenProcessor;
    private final UserRepository userRepository;
    private final BlackListTokenRepository blackListTokenRepository;

    public String getAuthCodeRequestUrl(String oauth2TypeName) {
        final OAuth2Handler oauth2Handler = oAuth2HandlerProvider.findHandler(oauth2TypeName);

        return oauth2Handler.provideAuthCodeRequestUrl();
    }

    public LoginResult loginWithAuthorizationCode(
            String oauth2TypeName,
            String authorizationCode,
            LocalDateTime requestTime
    ) {
        OAuth2Handler oauth2Handler = oAuth2HandlerProvider.findHandler(oauth2TypeName);
        String oauth2AccessToken = oauth2Handler.getOAuth2AccessToken(authorizationCode);
        OAuth2UserInfo oauth2UserInfo = oauth2Handler.getUserInfo(oauth2AccessToken);
        User user = findOrPersistUser(oauth2UserInfo);
        PrivateClaims privateClaims = new PrivateClaims(user.getId());
        Token token = tokenProcessor.generateToken(requestTime, privateClaims.toMap());

        return new LoginResult(user.getId(), token.accessToken(), token.refreshToken());
    }

    private User findOrPersistUser(OAuth2UserInfo oauth2UserInfo) {
        return userRepository.findByOauth2IdAndOauth2Type(
                oauth2UserInfo.oauth2Id(),
                oauth2UserInfo.oauth2Type()
        ).orElseGet(() -> {
            User newUser = new User(
                    oauth2UserInfo.nickname(),
                    oauth2UserInfo.profileImageUrl(),
                    oauth2UserInfo.oauth2Id(),
                    oauth2UserInfo.oauth2Type()
            );

            return userRepository.save(newUser);
        });
    }

    public PrivateClaims getValidPrivateClaims(TokenType tokenType, String token) {
        Claims claims = tokenProcessor.decode(tokenType, token)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));
        PrivateClaims privateClaims = PrivateClaims.from(claims);

        Long userId = privateClaims.userId();
        if (!userRepository.existsById(userId)) {
            throw new CustomException(ErrorCode.UNDEFINED_USER_TOKEN);
        }

        if (blackListTokenRepository.existsByUserIdAndToken(userId, token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN_ALREADY_LOGOUT);
        }

        return privateClaims;
    }

    public void logout(String accessToken, String refreshToken) {
        PrivateClaims privateClaims = getValidPrivateClaims(TokenType.ACCESS, accessToken);
        BlackListToken blacklistAccessToken = new BlackListToken(
                privateClaims.userId(),
                TokenType.ACCESS,
                accessToken
        );
        BlackListToken blacklistRefreshToken = new BlackListToken(
                privateClaims.userId(),
                TokenType.REFRESH,
                refreshToken
        );

        blackListTokenRepository.save(blacklistAccessToken);
        blackListTokenRepository.save(blacklistRefreshToken);
    }

    public boolean isValidToken(TokenType tokenType, String targetToken) {
        PrivateClaims privateClaims = tokenProcessor.decode(tokenType, targetToken)
                .map(PrivateClaims::from)
                .orElse(null);
        if (privateClaims == null) {
            return false;
        }

        return userRepository.existsById(privateClaims.userId()) &&
                !blackListTokenRepository.existsByUserIdAndToken(privateClaims.userId(), targetToken);
    }

    public TokenRefreshResult refreshToken(LocalDateTime requestTime, String refreshToken) {
        PrivateClaims privateClaims = getValidPrivateClaims(TokenType.REFRESH, refreshToken);
        Token token = tokenProcessor.generateToken(requestTime, privateClaims.toMap());

        BlackListToken blacklistRefreshToken = new BlackListToken(
                privateClaims.userId(),
                TokenType.REFRESH,
                refreshToken
        );
        blackListTokenRepository.save(blacklistRefreshToken);

        return new TokenRefreshResult(token.accessToken(), token.refreshToken());
    }
}