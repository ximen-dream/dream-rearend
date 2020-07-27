package com.ximen.common.validator;

import com.ximen.common.annotation.IsMobile;
import com.ximen.common.entity.RegexpConstant;
import com.ximen.common.utils.DreamUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:38
 * @note
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return DreamUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
