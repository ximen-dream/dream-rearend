package com.ximen.zuul.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author zhishun.cai
 * @date 2020/7/23 18:28
 * @note
 */
@EnableWebSecurity
public class DreamGatewaySecurityConfigure extends WebSecurityConfigurerAdapter {

    //因为febs-gateway引入了febs-common模块，febs-common模块包含了Spring Cloud Security依赖，
    // 所以我们需要定义一个自己的WebSecurity配置类，
    // 来覆盖默认的。这里主要是关闭了csrf功能，否则会报csrf相关异常。
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/actuator/**").permitAll();
    }
}
