package com.ximen.common.security.starter.configure;

import com.ximen.common.security.starter.properties.DreamCloudSecurityProperties;
import com.ximen.common.security.starter.interceptor.DreamServerProtectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhishun.cai
 * @date 2020/7/28 10:21
 * @note
 */
public class DreamCloudSecurityInteceptorConfigure implements WebMvcConfigurer {
    private DreamCloudSecurityProperties properties;

    @Autowired
    public void setProperties(DreamCloudSecurityProperties properties) {
        this.properties = properties;
    }

    @Bean
    public HandlerInterceptor dreamServerProtectInterceptor() {
        DreamServerProtectInterceptor dreamServerProtectInterceptor = new DreamServerProtectInterceptor();
        dreamServerProtectInterceptor.setProperties(properties);
        return dreamServerProtectInterceptor;
    }

    @Override
    @SuppressWarnings("all")
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dreamServerProtectInterceptor());
    }
}
