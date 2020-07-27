package com.ximen.auth.config;

import com.ximen.auth.filter.ValidateCodeFilter;
import com.ximen.auth.properties.DreamAuthProperties;
import com.ximen.common.handler.DreamAccessDeniedHandler;
import com.ximen.common.handler.DreamAuthExceptionEntryPoint;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author zhishun.cai
 * @date 2020/7/19 11:38
 * @note
 */
@Configuration
@EnableResourceServer
public class DreamResourceServerConfigure extends ResourceServerConfigurerAdapter {

    @Autowired
    private DreamAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private DreamAuthExceptionEntryPoint exceptionEntryPoint;
    @Autowired
    private DreamAuthProperties properties;



    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");
        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/**").authenticated()
                .and().httpBasic();;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
