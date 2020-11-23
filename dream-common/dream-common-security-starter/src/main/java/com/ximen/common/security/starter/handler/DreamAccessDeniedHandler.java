package com.ximen.common.security.starter.handler;

import com.ximen.common.core.utils.DreamUtil;
import com.ximen.common.core.entity.DreamResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhishun.cai
 * @date 2020/7/28 10:53
 * @note
 */
public class DreamAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        DreamResponse febsResponse = new DreamResponse();
        DreamUtil.makeJsonResponse(response, HttpServletResponse.SC_FORBIDDEN, febsResponse.message("没有权限访问该资源"));
    }
}
