package com.ximen.system.logging.annotation;

import com.ximen.system.logging.type.LogActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhishun.cai
 * @date 2021/6/17 16:11
 * @note 日志注解
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAno {

    /**
     * 描述信息
     * @return
     */
    String value() default "";

    /**
     * 是否启用(默认开启)
     *
     * @return
     */
    boolean enable() default true;

    /**
     * 默认查询
     * @return
     */
    LogActionType type() default LogActionType.SELECT;
}
