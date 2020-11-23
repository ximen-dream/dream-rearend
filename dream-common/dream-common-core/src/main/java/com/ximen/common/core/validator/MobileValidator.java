package com.ximen.common.core.validator;

import com.ximen.common.core.annotation.IsMobile;
import com.ximen.common.core.entity.constant.RegexpConstant;
import com.ximen.common.core.utils.DreamUtil;
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
