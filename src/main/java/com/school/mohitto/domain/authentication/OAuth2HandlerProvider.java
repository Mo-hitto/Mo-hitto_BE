package com.school.mohitto.domain.authentication;

import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Component
public class OAuth2HandlerProvider {
    private final Map<OAuth2Type, OAuth2Handler> handlerMappings;

    public OAuth2HandlerProvider(Set<OAuth2Handler> handlers) {
        this.handlerMappings = new EnumMap<>(OAuth2Type.class);
        for (OAuth2Handler handler : handlers) {
            OAuth2Type supportingOAuth2Type = handler.getSupportingOAuth2Type();
            handlerMappings.put(supportingOAuth2Type, handler);
        }
    }

    public OAuth2Handler findHandler(String oauth2TypeName) {
        final OAuth2Type oauth2Type = OAuth2Type.from(oauth2TypeName);

        return findHandler(oauth2Type);
    }

    public OAuth2Handler findHandler(OAuth2Type oauth2Type) {
        OAuth2Handler handler = handlerMappings.get(oauth2Type);
        if (handler == null) {
            throw new CustomException(ErrorCode.UNSUPPORTED_OAUTH2_TYPE);
        }

        return handler;
    }
}
