package com.ximen.auth.filter;

import com.ximen.auth.service.impl.ValidateCodeServiceImpl;
import com.ximen.common.core.entity.DreamResponse;
import com.ximen.common.core.entity.constant.EndpointConstant;
import com.ximen.common.core.entity.constant.GrantTypeConstant;
import com.ximen.common.core.entity.constant.ParamsConstant;
import com.ximen.common.core.exception.ValidateCodeException;
import com.ximen.common.core.utils.DreamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhishun.cai
 * @date 2020/7/25 0:12
 * @note
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Autowired
    private ValidateCodeServiceImpl validateCodeService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        RequestMatcher matcher = new AntPathRequestMatcher(EndpointConstant.OAUTH_TOKEN, HttpMethod.POST.toString());
        String grantType = httpServletRequest.getParameter(ParamsConstant.GRANT_TYPE);
        String grantType2 = httpServletRequest.getParameter("grant_type");
        System.out.println("grant_type: " + grantType);
        if (matcher.matches(httpServletRequest)
                && StringUtils.equalsIgnoreCase(httpServletRequest.getParameter(ParamsConstant.GRANT_TYPE), GrantTypeConstant.PASSWORD)) {
            try {
                validateCode(httpServletRequest);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (Exception e) {
                DreamResponse febsResponse = new DreamResponse();
                DreamUtil.makeFailureResponse(httpServletResponse, febsResponse.message(e.getMessage()));
                log.error(e.getMessage(), e);
            }
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private void validateCode(HttpServletRequest httpServletRequest) throws ValidateCodeException {
        String code = httpServletRequest.getParameter(ParamsConstant.VALIDATE_CODE_CODE);
        String key = httpServletRequest.getParameter(ParamsConstant.VALIDATE_CODE_KEY);
        validateCodeService.check(key, code);
    }
}
