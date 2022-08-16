
package com.sparta.miniproject_movie_study_01.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // 이걸 적용하니까 createdAt, modifiedAt 이상하게 나옴. 원인은 찾았으나
    // 해결은 못함. 일단 이렇게 사용해보자.
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .exposedHeaders("Authorization")
                .exposedHeaders("Refresh-Token")
                .maxAge(3000);


    }

}
