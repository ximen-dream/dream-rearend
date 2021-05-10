package com.ximen.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ximen.auth.entity.BindUser;
import com.ximen.auth.entity.UserConnection;
import com.ximen.auth.manager.UserManager;
import com.ximen.auth.properties.DreamAuthProperties;
import com.ximen.auth.service.SocialLoginService;
import com.ximen.auth.service.UserConnectionService;
import com.ximen.common.core.code.ResponseCode;
import com.ximen.common.core.entity.DreamResponse;
import com.ximen.common.core.entity.constant.GrantTypeConstant;
import com.ximen.common.core.entity.constant.ParamsConstant;
import com.ximen.common.core.entity.constant.SocialConstant;
import com.ximen.common.core.entity.system.SystemUser;
import com.ximen.common.core.exception.DreamException;
import com.ximen.common.core.utils.DreamUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhishun.cai
 * @date 2020/7/29 9:24
 * @note
 */
@Service
public class SocialLoginServiceImpl implements SocialLoginService {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String NOT_BIND = "not_bind";
    private static final String SOCIAL_LOGIN_SUCCESS = "social_login_success";

    @Autowired
    private UserManager userManager;

    @Autowired
    private AuthRequestFactory factory;

    @Autowired
    private DreamAuthProperties properties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserConnectionService userConnectionService;

    @Autowired
    private ResourceOwnerPasswordTokenGranter granter;

    @Autowired
    private RedisClientDetailsService redisClientDetailsService;

    @Override
    public AuthRequest renderAuth(String oauthType) throws DreamException {
        return factory.get(getAuthSource(oauthType));
    }

    @Override
    public DreamResponse resolveBind(String oauthType, AuthCallback callback) throws DreamException {
        DreamResponse DreamResponse = new DreamResponse();
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
        if (response.ok()) {
            DreamResponse.data(response.getData());
        } else {
            throw new DreamException(String.format("第三方登录失败，%s", response.getMsg()));
        }
        return DreamResponse;
    }

    @Override
    public DreamResponse resolveLogin(String oauthType, AuthCallback callback) throws DreamException {
        DreamResponse DreamResponse = new DreamResponse();
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
        if (response.ok()) {
            AuthUser authUser = (AuthUser) response.getData();
            UserConnection userConnection = userConnectionService.selectByCondition(authUser.getSource().toString(), authUser.getUuid());
            if (userConnection == null) {
                DreamResponse.message(NOT_BIND).data(authUser);
            } else {
                SystemUser user = userManager.findByName(userConnection.getUserName());
                if (user == null) {
                    throw new DreamException("系统中未找到与第三方账号对应的账户");
                }
                OAuth2AccessToken oAuth2AccessToken = getOauth2AccessToken(user);
                DreamResponse.message(SOCIAL_LOGIN_SUCCESS).data(oAuth2AccessToken);
                DreamResponse.put(USERNAME, user.getUsername());
            }
        } else {
            throw new DreamException(String.format("第三方登录失败，%s", response.getMsg()));
        }
        return DreamResponse;
    }

    @Override
    @Transactional
    public OAuth2AccessToken bindLogin(BindUser bindUser, AuthUser authUser) throws DreamException {
        SystemUser systemUser = userManager.findByName(bindUser.getBindUsername());
        if (systemUser == null || !passwordEncoder.matches(bindUser.getBindPassword(), systemUser.getPassword())) {
            throw new DreamException(ResponseCode.BIND_ERROR_USER_OR_PASSWORD);
        }
        this.createConnection(systemUser, authUser);
        return this.getOauth2AccessToken(systemUser);
    }

    @Override
    public OAuth2AccessToken signLogin(BindUser registUser, AuthUser authUser) throws DreamException {
        SystemUser user = this.userManager.findByName(registUser.getBindUsername());
        if (user != null) {
            throw new DreamException(ResponseCode.ERROR_EXIST_USERNAME);
        }
        String encryptPassword = passwordEncoder.encode(registUser.getBindPassword());
        SystemUser systemUser = this.userManager.registUser(registUser.getBindUsername(), encryptPassword,authUser.getAvatar());
        this.createConnection(systemUser, authUser);
        return this.getOauth2AccessToken(systemUser);
    }

    @Override
    public void bind(BindUser bindUser, AuthUser authUser) throws DreamException {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            UserConnection userConnection = userConnectionService.selectByCondition(authUser.getSource().toString(), authUser.getUuid());
            if (userConnection != null) {
                throw new DreamException("绑定失败，该第三方账号已绑定" + userConnection.getUserName() + "系统账户");
            }
            SystemUser systemUser = new SystemUser();
            systemUser.setUsername(username);
            this.createConnection(systemUser, authUser);
        } else {
            throw new DreamException("绑定失败，您无权绑定别人的账号");
        }
    }

    @Override
    public void unbind(BindUser bindUser, String oauthType) throws DreamException {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            this.userConnectionService.deleteByCondition(username, oauthType);
        } else {
            throw new DreamException("解绑失败，您无权解绑别人的账号");
        }
    }

    @Override
    public List<UserConnection> findUserConnections(String username) {
        return this.userConnectionService.selectByCondition(username);
    }

    private void createConnection(SystemUser systemUser, AuthUser authUser) {
        UserConnection userConnection = new UserConnection();
        userConnection.setUserName(systemUser.getUsername());
        userConnection.setProviderName(authUser.getSource().toString());
        userConnection.setProviderUserId(authUser.getUuid());
        userConnection.setProviderUserName(authUser.getUsername());
        userConnection.setImageUrl(authUser.getAvatar());
        userConnection.setNickName(authUser.getNickname());
        userConnection.setLocation(authUser.getLocation());
        this.userConnectionService.createUserConnection(userConnection);
    }

    private AuthCallback resolveAuthCallback(AuthCallback callback) {
        int stateLength = 3;
        String state = callback.getState();
        String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(state, "::");
        if (strings.length == stateLength) {
            callback.setState(strings[0] + "::" + strings[1]);
        }
        return callback;
    }

    private AuthSource getAuthSource(String type) throws DreamException {
        if (StrUtil.isNotBlank(type)) {
            return AuthSource.valueOf(type.toUpperCase());
        } else {
            throw new DreamException(String.format("暂不支持%s第三方登录", type));
        }
    }

    private boolean isCurrentUser(String username) {
        String currentUsername = DreamUtil.getCurrentUsername();
        return StringUtils.equalsIgnoreCase(username, currentUsername);
    }

    private OAuth2AccessToken getOauth2AccessToken(SystemUser user) throws DreamException {
        final HttpServletRequest httpServletRequest = DreamUtil.getHttpServletRequest();
        httpServletRequest.setAttribute(ParamsConstant.LOGIN_TYPE, SocialConstant.SOCIAL_LOGIN);
        String socialLoginClientId = properties.getSocialLoginClientId();
        ClientDetails clientDetails = null;
        try {
            clientDetails = redisClientDetailsService.loadClientByClientId(socialLoginClientId);
        } catch (Exception e) {
            throw new DreamException("获取第三方登录可用的Client失败");
        }
        if (clientDetails == null) {
            throw new DreamException("未找到第三方登录可用的Client");
        }
        Map<String, String> requestParameters = new HashMap<>(5);
        requestParameters.put(ParamsConstant.GRANT_TYPE, GrantTypeConstant.PASSWORD);
        requestParameters.put(USERNAME, user.getUsername());
        requestParameters.put(PASSWORD, SocialConstant.SOCIAL_LOGIN_PASSWORD);

        String grantTypes = String.join(",", clientDetails.getAuthorizedGrantTypes());
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientDetails.getClientId(), clientDetails.getScope(), grantTypes);
        return granter.grant(GrantTypeConstant.PASSWORD, tokenRequest);
    }
}
