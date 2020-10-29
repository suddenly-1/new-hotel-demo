package com.suddenly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurationSupport {

    @Bean
    public MyHandlerInterceptor myInterceptors() {
        return new MyHandlerInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptors())
                .addPathPatterns("/**");
    }
}
