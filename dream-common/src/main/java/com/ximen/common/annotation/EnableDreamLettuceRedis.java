package com.ximen.common.annotation;

import com.ximen.common.config.DreamLettuceRedisConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhishun.cai
 * @date 2020/7/24 23:45
 * @note
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DreamLettuceRedisConfigure.class)
public @interface EnableDreamLettuceRedis {
}
