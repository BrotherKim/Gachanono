package com.kaist.gachanono.gachanonoserver.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final HandlerInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginIntercepter loginIntercepter = new LoginIntercepter();
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(loginIntercepter.loginEssential)
                .excludePathPatterns(loginIntercepter.loginInessential);
    }
}