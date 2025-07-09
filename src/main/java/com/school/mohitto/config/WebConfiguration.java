package com.school.mohitto.config;

import com.school.mohitto.security.CorsProperties;
import com.school.mohitto.security.interceptor.LoginInterceptor;
import com.school.mohitto.security.resolver.AuthUserInfoArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final AuthUserInfoArgumentResolver authUserInfoArgumentResolver;
    private final CorsProperties corsProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/v3/api-docs/**")
                .excludePathPatterns("/favicon.ico", "/error")
                .excludePathPatterns("/oauth2/**")
                .excludePathPatterns("/health_check");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authUserInfoArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final String[] allowedHttpMethods = Arrays.stream(HttpMethod.values())
                .map(HttpMethod::name)
                .toArray(String[]::new);

        registry.addMapping("/**")
                .allowedOrigins(corsProperties.allowedOrigins())
                .allowedHeaders("*")
                .allowedMethods(allowedHttpMethods).exposedHeaders(HttpHeaders.LOCATION)
                .allowCredentials(true);
    }
}