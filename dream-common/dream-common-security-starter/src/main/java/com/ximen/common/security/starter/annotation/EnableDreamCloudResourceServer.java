package com.ximen.common.security.starter.annotation;

import com.ximen.common.security.starter.configure.DreamCloudResourceServerConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhishun.cai
 * @date 2020/7/28 11:32
 * @note 开启资源管理服务
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DreamCloudResourceServerConfigure.class)
public @interface EnableDreamCloudResourceServer {
}
