package com.kaist.gachanono.gachanonoserver.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1-definition")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Gachanono API")
                        .description("가챠노노 API 명세서")
                        .version("v0.0.1"));
    }
    // @Bean
    // public Docket api() {
    //     return new Docket(DocumentationType.SWAGGER_2)
    //     .select()
    //     .apis(RequestHandlerSelectors.basePackage("com.kaist.gachanono.gachanonoserver.controller"))
    //     .paths(PathSelectors.any())
    //     .build();
    // }
}
