package com.ximen.auth;

import com.ximen.common.annotation.DreamCloudApplication;
import com.ximen.common.annotation.EnableDreamAuthExceptionHandler;
import com.ximen.common.annotation.EnableDreamLettuceRedis;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhishun.cai
 * @date 2020/7/19 11:27
 * @note
 */
@SpringBootApplication
@EnableDreamAuthExceptionHandler
@EnableDreamLettuceRedis
@DreamCloudApplication
@MapperScan("com.ximen.auth.mapper")
public class DreamAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(DreamAuthApplication.class, args);
    }
}
