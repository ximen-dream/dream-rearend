package com.ximen.gateway.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author zhishun.cai
 * @date 2020/7/29 16:34
 * @note
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    public Mono<String> index() {
        return Mono.just("dream cloud gateway");
    }
}
