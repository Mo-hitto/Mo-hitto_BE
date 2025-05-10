package com.school.mohitto.service.authentication;

import com.school.mohitto.domain.User;
import com.school.mohitto.domain.authentication.*;
import com.school.mohitto.repository.UserRepository;
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

}