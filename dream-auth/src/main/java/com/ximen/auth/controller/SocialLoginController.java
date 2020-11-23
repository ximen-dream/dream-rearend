package com.ximen.auth.controller;

import com.ximen.auth.entity.BindUser;
import com.ximen.auth.entity.UserConnection;
import com.ximen.auth.manager.UserManager;
import com.ximen.auth.service.SocialLoginService;
import com.ximen.common.core.entity.DreamResponse;
import com.ximen.common.core.entity.constant.StringConstant;
import com.ximen.common.core.entity.system.SystemUser;
import com.ximen.common.core.exception.DreamException;
import com.ximen.common.core.utils.DreamUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

/**
 * @author zhishun.cai
 * @date 2020/7/29 9:51
 * @note
 */
@Slf4j
@Controller
@RequestMapping("social")
public class SocialLoginController {

    private static final String TYPE_LOGIN = "login";
    private static final String TYPE_BIND = "bind";

    @Autowired
    private SocialLoginService socialLoginService;
    @Autowired
    private UserManager userManager;
    @Value("${dream.frontUrl}")
    private String frontUrl;

    /**
     * 登录
     *
     * @param oauthType 第三方登录类型
     * @param response  response
     */
    @ResponseBody
    @GetMapping("/login/{oauthType}/{type}")
    public void renderAuth(@PathVariable String oauthType, @PathVariable String type, HttpServletResponse response) throws IOException, DreamException {
        AuthRequest authRequest = socialLoginService.renderAuth(oauthType);
        String str = authRequest.authorize(oauthType + StringConstant.DOUBLE_COLON + AuthStateUtils.createState()) + "::" + type;
        log.info(str);
        response.sendRedirect(str);
    }

    /**
     * 登录成功后的回调
     *
     * @param oauthType 第三方登录类型
     * @param callback  携带返回的信息
     * @return String
     */
    @GetMapping("/{oauthType}/callback")
    public String login(@PathVariable String oauthType, AuthCallback callback, String state, Model model) {
        try {
            DreamResponse DreamResponse = null;
            String type = StringUtils.substringAfterLast(state, StringConstant.DOUBLE_COLON);
            if (StringUtils.equals(type, TYPE_BIND)) {
                DreamResponse = socialLoginService.resolveBind(oauthType, callback);
            } else {
                DreamResponse = socialLoginService.resolveLogin(oauthType, callback);
            }
            model.addAttribute("response", DreamResponse);
            model.addAttribute("frontUrl", frontUrl);
            return "result";
        } catch (Exception e) {
            String errorMessage = DreamUtil.containChinese(e.getMessage()) ? e.getMessage() : "第三方登录失败";
            model.addAttribute("error", "第三方登录失败");
            return "fail";
        }
    }

    /**
     * 绑定并登录
     *
     * @param user user
     * @param authUser authUser
     * @return DreamResponse
     */
    @ResponseBody
    @PostMapping("bind/login")
    public DreamResponse bindLogin(@Valid BindUser user, AuthUser authUser) throws DreamException {
        OAuth2AccessToken oAuth2AccessToken = this.socialLoginService.bindLogin(user, authUser);
        return new DreamResponse().data(oAuth2AccessToken);
    }

    /**
     * 注册并登录
     *
     * @param user user
     * @param authUser   authUser
     * @return DreamResponse
     */
    @ResponseBody
    @PostMapping("sign/login")
    public DreamResponse signLogin(@Valid BindUser user, AuthUser authUser) throws DreamException {
        //判断用户名是否存在
        SystemUser exitUser = this.userManager.findByName(user.getBindUsername());
        OAuth2AccessToken oAuth2AccessToken = this.socialLoginService.signLogin(user, authUser);
        return new DreamResponse().data(oAuth2AccessToken);
    }

    /**
     * 绑定
     *
     * @param bindUser bindUser
     * @param authUser authUser
     */
    @ResponseBody
    @PostMapping("bind")
    public void bind(BindUser bindUser, AuthUser authUser) throws DreamException {
        this.socialLoginService.bind(bindUser, authUser);
    }

    /**
     * 解绑
     *
     * @param bindUser  bindUser
     * @param oauthType oauthType
     */
    @ResponseBody
    @DeleteMapping("unbind")
    public void unbind(BindUser bindUser, String oauthType) throws DreamException {
        this.socialLoginService.unbind(bindUser, oauthType);
    }

    /**
     * 根据用户名获取绑定关系
     *
     * @param username 用户名
     * @return DreamResponse
     */
    @ResponseBody
    @GetMapping("connections/{username}")
    public DreamResponse findUserConnections(@NotBlank(message = "{required}") @PathVariable String username) {
        List<UserConnection> userConnections = this.socialLoginService.findUserConnections(username);
        return new DreamResponse().data(userConnections);
    }
}
