package com.ximen.auth;

import com.ximen.common.security.starter.annotation.EnableDreamCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhishun.cai
 * @date 2020/7/19 11:27
 * @note
 */
@SpringBootApplication
@EnableDreamCloudResourceServer
//@DreamCloudApplication
@MapperScan("com.ximen.auth.mapper")
public class DreamAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(DreamAuthApplication.class, args);
    }
}
