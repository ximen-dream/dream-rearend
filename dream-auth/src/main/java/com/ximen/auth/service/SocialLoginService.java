package com.ximen.auth.service;

import com.ximen.auth.entity.BindUser;
import com.ximen.auth.entity.UserConnection;
import com.ximen.common.core.entity.DreamResponse;
import com.ximen.common.core.exception.DreamException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.List;

/**
 * @author zhishun.cai
 * @date 2020/7/29 9:13
 * @note
 */
public interface SocialLoginService {
    /**
     * 解析第三方登录请求
     *
     * @param oauthType 第三方平台类型
     * @return AuthRequest
     * @throws DreamException 异常
     */
    AuthRequest renderAuth(String oauthType) throws DreamException;

    /**
     * 处理第三方登录（绑定页面）
     *
     * @param oauthType 第三方平台类型
     * @param callback  回调
     * @return FebsResponse
     * @throws DreamException 异常
     */
    DreamResponse resolveBind(String oauthType, AuthCallback callback) throws DreamException;

    /**
     * 处理第三方登录（登录页面）
     *
     * @param oauthType 第三方平台类型
     * @param callback  回调
     * @return FebsResponse
     * @throws DreamException 异常
     */
    DreamResponse resolveLogin(String oauthType, AuthCallback callback) throws DreamException;

    /**
     * 绑定并登录
     *
     * @param bindUser 绑定用户
     * @param authUser 第三方平台对象
     * @return OAuth2AccessToken 令牌对象
     * @throws DreamException 异常
     */
    OAuth2AccessToken bindLogin(BindUser bindUser, AuthUser authUser) throws DreamException;

    /**
     * 注册并登录
     *
     * @param registUser 注册用户
     * @param authUser   第三方平台对象
     * @return OAuth2AccessToken 令牌对象
     * @throws DreamException 异常
     */
    OAuth2AccessToken signLogin(BindUser registUser, AuthUser authUser) throws DreamException;

    /**
     * 绑定
     *
     * @param bindUser 绑定对象
     * @param authUser 第三方平台对象
     * @throws DreamException 异常
     */
    void bind(BindUser bindUser, AuthUser authUser) throws DreamException;

    /**
     * 解绑
     *
     * @param bindUser  绑定对象
     * @param oauthType 第三方平台对象
     * @throws DreamException 异常
     */
    void unbind(BindUser bindUser, String oauthType) throws DreamException;

    /**
     * 根据用户名获取绑定关系
     *
     * @param username 用户名
     * @return 绑定关系集合
     */
    List<UserConnection> findUserConnections(String username);
}
