package com.ximen.system.logging.service.Impl;

import com.ximen.common.core.entity.logging.Log;
import com.ximen.system.logging.annotation.LogAno;
import com.ximen.system.logging.mapper.LoggingMapper;
import com.ximen.system.logging.service.LoggingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhishun.cai
 * @date 2021/6/17 16:20
 */

@Service
public class LoggingServiceImpl implements LoggingService {

    @Autowired
    private LoggingMapper loggingMapper;

    @Override
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAno aopLog = method.getAnnotation(LogAno.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        List<Object> argValues = new ArrayList<>(Arrays.asList(joinPoint.getArgs()));
        //参数名称
        for (Object argValue : argValues) {
            params.append(argValue).append(" ");
        }
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);

        String loginPath = "login";
//        if (loginPath.equals(signature.getName())) {
//            try {
//                username = new JSONObject(argValues.get(0)).get("username").toString();
//            } catch (Exception e) {
//                LogServiceImpl.log.error(e.getMessage(), e);
//            }
//        }
//        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        loggingMapper.insert(log);
    }
}
