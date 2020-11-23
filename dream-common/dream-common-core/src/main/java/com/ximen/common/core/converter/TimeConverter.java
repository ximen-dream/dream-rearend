package com.ximen.common.core.converter;

import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import com.ximen.common.core.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;

/**
 * @author zhishun.cai
 * @date 2020/7/29 9:36
 * @note Execl导出时间类型字段格式化
 */
@Slf4j
public class TimeConverter implements WriteConverter {
    @Override
    public String convert(Object value) {
        if (value == null) {
            return StringUtils.EMPTY;
        } else {
            try {
                return DateUtil.formatCstTime(value.toString(), DateUtil.FULL_TIME_SPLIT_PATTERN);
            } catch (ParseException e) {
                String message = "时间转换异常";
                log.error(message, e);
                throw new ExcelKitWriteConverterException(message);
            }
        }
    }
}
