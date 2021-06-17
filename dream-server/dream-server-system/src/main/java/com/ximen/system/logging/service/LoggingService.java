package com.ximen.system.logging.service;

import com.ximen.common.core.entity.logging.Log;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author zhishun.cai
 * @date 2021/6/17 16:20
 * @note
 */

public interface LoggingService {
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log);
}
