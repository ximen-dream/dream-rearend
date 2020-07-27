package com.ximen.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author zhishun.cai
 * @date 2020/7/19 11:02
 * @note
 */
@EnableEurekaServer
@SpringBootApplication
public class DreamRegisterEureka {
    public static void main(String[] args) {
        SpringApplication.run(DreamRegisterEureka.class, args);
    }
}
