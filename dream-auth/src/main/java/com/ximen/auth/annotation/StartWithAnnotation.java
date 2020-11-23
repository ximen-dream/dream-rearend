package com.ximen.auth.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zhishun.cai
 * @date 2020/7/30 16:06
 * @note
 */
@Documented
@Constraint(validatedBy = StartWith.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface StartWithAnnotation {
    String message() default "不是damon一族";

    String start() default "_";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
