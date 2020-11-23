package com.ximen.auth.handler;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.ximen.common.core.handler.BaseExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhishun.cai
 * @date 2020/7/24 17:05
 * @note 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler{

}
