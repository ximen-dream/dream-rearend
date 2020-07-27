package com.ximen.common.config;

import com.ximen.common.entity.DreamConstant;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.Base64Utils;

/**
 * @author zhishun.cai
 * @date 2020/7/24 17:40
 * @note
 */
public class DreamOAuth2FeignConfigure {
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
            //经过网关标识
            String zuulToken = new String(Base64Utils.encode(DreamConstant.ZUUL_TOKEN_VALUE.getBytes()));
            requestTemplate.header(DreamConstant.ZUUL_TOKEN_HEADER, zuulToken);
            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof OAuth2AuthenticationDetails) {
                String authorizationToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
                requestTemplate.header(HttpHeaders.AUTHORIZATION, "bearer " + authorizationToken);
            }
        };
    }
}
