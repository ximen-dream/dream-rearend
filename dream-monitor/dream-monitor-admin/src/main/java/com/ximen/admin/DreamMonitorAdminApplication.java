package com.ximen.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhishun.cai
 * @date 2020/7/25 22:50
 * @note
 */
@EnableAdminServer
@SpringBootApplication
public class DreamMonitorAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(DreamMonitorAdminApplication.class,args);
    }
}
