package com.ximen.gateway.common.controller;

import com.ximen.common.core.entity.DreamResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author zhishun.cai
 * @date 2020/7/29 16:33
 * @note
 */
@Slf4j
@RestController
public class FallbackController {
    @RequestMapping("fallback/{name}")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<DreamResponse> systemFallback(@PathVariable String name) {
        String response = "服务访问超时，请稍后再试";
        log.error("{}，目标微服务：{}", response, name);
        return Mono.just(new DreamResponse().message(response));
    }
}
