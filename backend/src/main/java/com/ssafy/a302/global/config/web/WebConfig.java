package com.ssafy.a302.global.config.web;

import com.ssafy.a302.global.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.File;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${path.images}")
    private String uploadImagePath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * images 디렉터리 하위에 존재하는 디렉터리 리스트 생성
         */
        List<String> imageFolders = List.of("profile");

        /**
         * images 디렉터리 하위에 존재하는 디렉터리 이름을 순회하면서 데이터 접근 경로를 재설정한다.
         */
        for (String imageFolder : imageFolders) {
            registry.addResourceHandler("/static/images/" + imageFolder + "/**")
                    .addResourceLocations("file://" + uploadImagePath + imageFolder + File.separator)
                    .setCachePeriod(3600)
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver());
        }
    }
}