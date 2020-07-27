package com.ximen.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author zhishun.cai
 * @date 2020/7/24 13:32
 * @note
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("info")
    public String test(){
        return "febs-server-system";
    }

    @GetMapping("hello")
    public String hello(String name) {
        log.info("/hello服务被调用");
        return "hello" + name;
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }
}
