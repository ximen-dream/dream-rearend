package com.ximen.common.security.starter.configure;

import com.ximen.common.core.utils.DreamUtil;
import com.ximen.common.core.entity.constant.DreamConstant;
import com.ximen.common.security.starter.handler.DreamAccessDeniedHandler;
import com.ximen.common.security.starter.handler.DreamAuthExceptionEntryPoint;
import com.ximen.common.security.starter.properties.DreamCloudSecurityProperties;
import feign.RequestInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Base64Utils;

/**
 * @author zhishun.cai
 * @date 2020/7/28 10:18
 * @note 自动装配类
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(DreamCloudSecurityProperties.class)
@ConditionalOnProperty(value = "dream.cloud.security.enable", havingValue = "true", matchIfMissing = true)
public class DreamCloudSecurityAutoConfigure {

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

    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DreamCloudSecurityInteceptorConfigure dreamCloudSecurityInteceptorConfigure() {
        return new DreamCloudSecurityInteceptorConfigure();
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
            String gatewayToken = new String(Base64Utils.encode(DreamConstant.GATEWAY_TOKEN_VALUE.getBytes()));
            requestTemplate.header(DreamConstant.GATEWAY_TOKEN_HEADER, gatewayToken);
            String authorizationToken = DreamUtil.getCurrentTokenValue();
            if (StringUtils.isNotBlank(authorizationToken)) {
                requestTemplate.header(HttpHeaders.AUTHORIZATION, DreamConstant.OAUTH2_TOKEN_TYPE + authorizationToken);
            }
        };
    }
}
