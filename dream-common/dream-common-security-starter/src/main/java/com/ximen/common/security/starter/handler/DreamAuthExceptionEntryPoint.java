package com.ximen.common.security.starter.handler;

import com.ximen.common.core.utils.DreamUtil;
import com.ximen.common.core.entity.DreamResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhishun.cai
 * @date 2020/7/28 10:54
 * @note
 */
@Slf4j
public class DreamAuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String requestUri = request.getRequestURI();
        int status = HttpServletResponse.SC_UNAUTHORIZED;
        String header = request.getHeader("Authorization");
        System.out.println("header: " + header);
        String message = "访问令牌不合法";
        log.error("客户端访问{}请求失败: {}", requestUri, message, authException);
        DreamUtil.makeJsonResponse(response, status, new DreamResponse().message(message));
    }
}
