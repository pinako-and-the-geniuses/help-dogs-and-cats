package com.ssafy.a302.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 서버 응답 시 json을 자바스크립트에서 처리하 수 있게 한다.
        config.setAllowCredentials(true);
        // 모든 IP에 응답 허용
        config.addAllowedOriginPattern("*");
        // 모든 Header에 응답 허용
        config.addAllowedHeader("*");
        // 모든 HTTP 요청 허용
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }

}
