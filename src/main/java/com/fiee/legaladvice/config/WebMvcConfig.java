package com.fiee.legaladvice.config;

import com.fiee.legaladvice.handle.WebMvcHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Fiee
 * @ClassName: WebMvcConfig
 * @Date: 2024/4/17
 * @Version: v1.0.0
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public WebMvcHandler getWebSecurityHandler() {
        return new WebMvcHandler();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowedMethods("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new PageableHandlerInterceptor());
        registry.addInterceptor(getWebSecurityHandler());
    }
}
