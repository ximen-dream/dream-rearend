package com.ximen.common.config;

import com.ximen.common.handler.DreamAccessDeniedHandler;
import com.ximen.common.handler.DreamAuthExceptionEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author zhishun.cai
 * @date 2020/7/24 16:14
 * @note
 */
public class DreamAuthExceptionConfigure {

    /**
     * ConditionalOnMissingBean注解的意思是，当IOC容器中没有指定名称或类型的Bean的时候，就注册它。
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public DreamAccessDeniedHandler accessDeniedHandler() {
        return new DreamAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public DreamAuthExceptionEntryPoint authenticationEntryPoint() {
        return new DreamAuthExceptionEntryPoint();
    }
}
