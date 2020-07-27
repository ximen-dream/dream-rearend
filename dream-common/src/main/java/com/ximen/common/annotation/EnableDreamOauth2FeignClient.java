package com.ximen.common.annotation;

import com.ximen.common.config.DreamOAuth2FeignConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhishun.cai
 * @date 2020/7/24 17:43
 * @note 开启带令牌的Feign请求，避免微服务内部调用出现401异常；
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DreamOAuth2FeignConfigure.class)
public @interface EnableDreamOauth2FeignClient {
}
