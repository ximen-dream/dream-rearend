package com.ximen.system;

import com.ximen.common.security.starter.annotation.EnableDreamCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhishun.cai
 * @date 2020/7/24 12:56
 * @note
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableTransactionManagement
@EnableDreamCloudResourceServer
@MapperScan({"com.ximen.system.system.mapper","com.ximen.system.job.mapper","com.ximen.system.logging.mapper"})
public class DreamServerSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(DreamServerSystemApplication.class, args);
    }
}
