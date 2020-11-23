package com.ximen.system.config;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.ximen.common.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:18
 * @note
 */
public class P6spySqlFormatConfigure implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return StringUtils.isNotBlank(sql) ? DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN)
                + " | 耗时 " + elapsed + " ms | SQL 语句：" + StringUtils.LF + sql.replaceAll("[\\s]+", StringUtils.SPACE) + ";" : StringUtils.EMPTY;
    }
}
