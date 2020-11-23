package com.ximen.auth.annotation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zhishun.cai
 * @date 2020/7/30 16:05
 * @note
 */
public class StartWith implements ConstraintValidator<StartWithAnnotation, String> {
    String start;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("value ； " + value);
        if(StringUtils.isEmpty(value)){
            return false;
        }
        return StringUtils.startsWith(value,start);
    }

    @Override
    public void initialize(StartWithAnnotation constraintAnnotation) {
        start = constraintAnnotation.start();
    }
}
