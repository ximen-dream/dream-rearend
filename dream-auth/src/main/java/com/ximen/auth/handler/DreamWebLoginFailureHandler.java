package com.ximen.auth.handler;

import com.ximen.common.core.entity.DreamResponse;
import com.ximen.common.core.utils.DreamUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhishun.cai
 * @date 2020/7/28 9:02
 * @note 登录失败处理
 */
@Component
public class DreamWebLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException {
        String message;
        if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误！";
        } else if (exception instanceof LockedException) {
            message = "用户已被锁定！";
        } else {
            message = "认证失败，请联系网站管理员！";
        }
        DreamResponse febsResponse = new DreamResponse().message(message);
        DreamUtil.makeFailureResponse(httpServletResponse, febsResponse);
    }
}
