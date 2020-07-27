package com.ximen.common.annotation;

import com.ximen.common.selector.DreamCloudApplicationSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhishun.cai
 * @date 2020/7/24 18:13
 * @note
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DreamCloudApplicationSelector.class)
public @interface DreamCloudApplication {
}
