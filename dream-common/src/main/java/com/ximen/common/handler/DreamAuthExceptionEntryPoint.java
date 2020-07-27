package com.ximen.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.ximen.common.entity.DreamResponse;
import com.ximen.common.utils.DreamUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhishun.cai
 * @date 2020/7/24 15:53
 * @note
 */
public class DreamAuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        DreamResponse response = new DreamResponse();
        DreamUtil.makeResponse(httpServletResponse, MediaType.APPLICATION_JSON_UTF8_VALUE,HttpServletResponse.SC_UNAUTHORIZED,response.message("token无效"));//403状态码
    }
}
