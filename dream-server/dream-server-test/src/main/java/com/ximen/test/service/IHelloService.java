package com.ximen.test.service;

import com.ximen.common.entity.DreamServerConstant;
import com.ximen.test.fallback.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhishun.cai
 * @date 2020/7/24 17:25
 * @note
 */
@FeignClient(value = DreamServerConstant.DREAM_SERVER_SYSTEM, contextId = "helloServiceClient", fallbackFactory = HelloServiceFallback.class)
public interface IHelloService {
    @GetMapping("test/hello")
    String hello(@RequestParam("name") String name);
}
