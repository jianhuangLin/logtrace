package com.hong.interceptor.config;

import com.hong.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
//    "/upload/**","/log/**",
    private static final List<String> EXCLUDE_PATH = Arrays.asList("/","/login","/**/*.css","/**/*.js",
        "/**/*.png","/**/*.jpg");

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns(EXCLUDE_PATH);
    }

}