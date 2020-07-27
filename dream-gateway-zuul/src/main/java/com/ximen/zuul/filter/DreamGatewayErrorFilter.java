package com.ximen.zuul.filter;

import com.netflix.zuul.context.RequestContext;
import com.ximen.common.entity.DreamResponse;
import com.ximen.common.utils.DreamUtil;
import io.lettuce.core.dynamic.support.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhishun.cai
 * @date 2020/7/24 16:53
 * @note
 */
@Slf4j
@Component
public class DreamGatewayErrorFilter extends SendErrorFilter {
    @Override
    public Object run() {
        try {
            DreamResponse dreamResponse = new DreamResponse();
            RequestContext ctx = RequestContext.getCurrentContext();
            String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);

            ExceptionHolder exception = findZuulException(ctx.getThrowable());
            String errorCause = exception.getErrorCause();
            Throwable throwable = exception.getThrowable();
            String message = throwable.getMessage();
            message = StringUtils.isBlank(message) ? errorCause : message;
            dreamResponse = resolveExceptionMessage(message, serviceId, dreamResponse);

            HttpServletResponse response = ctx.getResponse();
            DreamUtil.makeResponse(
                    response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, dreamResponse
            );
            log.error("Zull sendError：{}", dreamResponse.getMessage());
        } catch (Exception ex) {
            log.error("Zuul sendError", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

    private DreamResponse resolveExceptionMessage(String message, String serviceId, DreamResponse febsResponse) {
        if (StringUtils.containsIgnoreCase(message, "time out")) {
            return febsResponse.message("请求" + serviceId + "服务超时");
        }
        if (StringUtils.containsIgnoreCase(message, "forwarding error")) {
            return febsResponse.message(serviceId + "服务不可用");
        }
        return febsResponse.message("Zuul请求" + serviceId + "服务异常");
    }
}
