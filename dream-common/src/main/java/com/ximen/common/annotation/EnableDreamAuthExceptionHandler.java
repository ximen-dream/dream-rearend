package com.ximen.common.annotation;

import com.ximen.common.config.DreamAuthExceptionConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhishun.cai
 * @date 2020/7/24 16:30
 * @note 认证类型异常翻译
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DreamAuthExceptionConfigure.class)
public @interface EnableDreamAuthExceptionHandler {
}
