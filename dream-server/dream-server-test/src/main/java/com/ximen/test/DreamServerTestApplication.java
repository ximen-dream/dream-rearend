package com.ximen.test;

import com.ximen.common.security.starter.annotation.EnableDreamCloudResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhishun.cai
 * @date 2020/7/24 13:35
 * @note
 */
@EnableFeignClients
@SpringBootApplication
@EnableTransactionManagement
@EnableDreamCloudResourceServer
public class DreamServerTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(DreamServerTestApplication.class, args);
    }
}
