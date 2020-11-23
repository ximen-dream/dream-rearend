package com.ximen.auth.controller;

import com.sun.istack.NotNull;
import com.ximen.auth.Person;
import com.ximen.auth.annotation.StartWithAnnotation;
import com.ximen.auth.service.impl.ValidateCodeServiceImpl;
import com.ximen.common.core.entity.DreamResponse;
import com.ximen.common.core.exception.DreamAuthException;
import com.ximen.common.core.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

/**
 * @author zhishun.cai
 * @date 2020/7/19 11:46
 * @note
 */
@RestController
public class SecurityController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    private ValidateCodeServiceImpl validateCodeService;

    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        validateCodeService.create(request, response);
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    @DeleteMapping("signout")
    public DreamResponse signout(HttpServletRequest request) throws DreamAuthException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "bearer ", "");
        DreamResponse febsResponse = new DreamResponse();
        if (!consumerTokenServices.revokeToken(token)) {
            throw new DreamAuthException("退出登录失败");
        }
        return febsResponse.message("退出登录成功");
    }
    @GetMapping("testAno")
    public String testAno(@Validated @RequestBody Person person){
        return "Ok";
    }
}
