package com.ximen.test;

import com.ximen.common.annotation.DreamCloudApplication;
import com.ximen.common.annotation.EnableDreamAuthExceptionHandler;
import com.ximen.common.annotation.EnableDreamOauth2FeignClient;
import com.ximen.common.annotation.EnableDreamServerProtect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author zhishun.cai
 * @date 2020/7/24 13:35
 * @note
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@DreamCloudApplication
public class DreamServerTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(DreamServerTestApplication.class, args);
    }
}
