package com.ximen.system.logging.aspect;

import com.ximen.common.core.entity.logging.Log;
import com.ximen.common.core.utils.RequestHolder;
import com.ximen.system.logging.service.LoggingService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhishun.cai
 * @date 2021/6/17 16:24
 */

@Component
@Aspect
@Slf4j
public class LogAspect {

    private final LoggingService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(LoggingService logService) {
        this.logService = logService;
    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.ximen.system.logging.annotation.LogAno)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.warn(" ==== logAround  ====");
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Log log = new Log("INFO",System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save("damoncai", "123", "123",joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = new Log("ERROR",System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(e.getMessage().getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save("damoncai", "123", "123", (ProceedingJoinPoint)joinPoint, log);
    }
}
