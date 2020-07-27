package com.ximen.common.config;

import com.ximen.common.interceptor.DreamServerProtectInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhishun.cai
 * @date 2020/7/24 17:59
 * @note
 */
public class DreamServerProtectConfigure implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor dreamServerProtectInterceptor() {
        return new DreamServerProtectInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dreamServerProtectInterceptor());
    }
}
