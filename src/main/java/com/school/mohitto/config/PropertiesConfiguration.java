package com.school.mohitto.config;

import com.school.mohitto.security.CorsProperties;
import com.school.mohitto.security.jwt.JwtProperties;
import com.school.mohitto.security.oauth2.KakaoOAuth2Properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        JwtProperties.class,
        KakaoOAuth2Properties.class,
        CorsProperties.class
})
public class PropertiesConfiguration{

}