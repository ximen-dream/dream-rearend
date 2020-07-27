package com.ximen.common.annotation;

import com.ximen.common.config.DreamServerProtectConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhishun.cai
 * @date 2020/7/24 18:02
 * @note 开启微服务防护，避免客户端绕过网关直接请求微服务；
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DreamServerProtectConfigure.class)
public @interface EnableDreamServerProtect {
}
