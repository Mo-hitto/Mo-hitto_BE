package com.school.mohitto.config;

import com.school.mohitto.aws.s3.S3Properties;
import com.school.mohitto.naver.NaverProperties;
import com.school.mohitto.security.CorsProperties;
import com.school.mohitto.security.jwt.JwtProperties;
import com.school.mohitto.security.oauth2.KakaoOAuth2Properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        JwtProperties.class,
        KakaoOAuth2Properties.class,
        CorsProperties.class,
        S3Properties.class,
        NaverProperties.class,
})
public class PropertiesConfiguration{

}