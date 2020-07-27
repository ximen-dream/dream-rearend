package com.ximen.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author zhishun.cai
 * @date 2020/7/24 18:18
 * @note
 */
@Configuration
public class DreamGateWayCorsConfigure {
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);//表示允许cookie跨域；
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);//表示请求头部允许携带任何内容；
        corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);//表示允许任何来源；
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);//表示允许任何HTTP方法。
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
