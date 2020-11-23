package com.ximen.common.security.starter.interceptor;

import com.ximen.common.core.entity.DreamResponse;
import com.ximen.common.core.entity.constant.DreamConstant;
import com.ximen.common.security.starter.properties.DreamCloudSecurityProperties;
import com.ximen.common.core.utils.DreamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhishun.cai
 * @date 2020/7/28 10:23
 * @note 防止跳过网关直接访问服务
 */

public class DreamServerProtectInterceptor implements HandlerInterceptor {
    private DreamCloudSecurityProperties properties;
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        if (!properties.getOnlyFetchByGateway()) {
            return true;
        }
        String token = request.getHeader(DreamConstant.GATEWAY_TOKEN_HEADER);
        String gatewayToken = new String(Base64Utils.encode(DreamConstant.GATEWAY_TOKEN_VALUE.getBytes()));
        if (StringUtils.equals(gatewayToken, token)) {
            return true;
        } else {
            DreamResponse febsResponse = new DreamResponse();
            DreamUtil.makeJsonResponse(response, HttpServletResponse.SC_FORBIDDEN, febsResponse.message("请通过网关获取资源"));
            return false;
        }
    }

    public void setProperties(DreamCloudSecurityProperties properties) {
        this.properties = properties;
    }
}
