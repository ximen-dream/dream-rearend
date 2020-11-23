package com.ximen.common.core.entity.constant;

/**
 * @author zhishun.cai
 * @date 2020/7/28 15:38
 * @note
 */
public interface GrantTypeConstant {
    /**
     * 刷新模式
     */
    String REFRESH_TOKEN = "refresh_token";
    /**
     * 授权码模式
     */
    String AUTHORIZATION_CODE = "authorization_code";
    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";
    /**
     * 密码模式
     */
    String PASSWORD = "password";
    /**
     * 简化模式
     */
    String IMPLICIT = "implicit";
}
