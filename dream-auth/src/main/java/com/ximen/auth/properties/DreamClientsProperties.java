package com.ximen.auth.properties;

import lombok.Data;

/**
 * @author zhishun.cai
 * @date 2020/7/24 14:35
 * @note
 */
@Data
public class DreamClientsProperties {

    /**
     * client_id
     */
    private String client;

    /**
     * secret_id
     */
    private String secret;

    /**
     * 对应当前令牌支持的认证类型
     */
    private String grantType = "password,authorization_code,refresh_token";

    /**
     * scope对应认证范围
     */
    private String scope = "all";
}
