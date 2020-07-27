package com.ximen.test.config;

import com.ximen.common.handler.DreamAccessDeniedHandler;
import com.ximen.common.handler.DreamAuthExceptionEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author zhishun.cai
 * @date 2020/7/24 13:36
 * @note
 */
@Configuration
@EnableResourceServer
public class DreamServerTestResourceServerConfigure extends ResourceServerConfigurerAdapter {

    @Autowired
    private DreamAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private DreamAuthExceptionEntryPoint exceptionEntryPoint;
    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .tokenStore(tokenStore)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
