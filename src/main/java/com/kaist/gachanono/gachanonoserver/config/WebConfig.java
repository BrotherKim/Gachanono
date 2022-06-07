package com.kaist.gachanono.gachanonoserver.config;

import lombok.RequiredArgsConstructor;

import java.util.List;

import com.kaist.gachanono.gachanonoserver.config.auth.LoginUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    /* HandlerMethodArgumentResolver는 항상 addArgumentResolvers()를 통해 추가해야 함 */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }
}