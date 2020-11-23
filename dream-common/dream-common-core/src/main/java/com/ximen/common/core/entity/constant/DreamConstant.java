package com.ximen.common.core.entity.constant;

/**
 * @author zhishun.cai
 * @date 2020/7/24 17:55
 * @note
 */
public interface DreamConstant {

    /**
     * Gateway请求头TOKEN名称（不要有空格）
     */
    String GATEWAY_TOKEN_HEADER = "GatewayToken";
    /**
     * Gateway请求头TOKEN值
     */
    String GATEWAY_TOKEN_VALUE = "dream:gateway:123456";

    /**
     * OAUTH2 令牌类型 https://oauth.net/2/bearer-tokens/
     */
    String OAUTH2_TOKEN_TYPE = "bearer";

    /**
     * 异步线程池名称
     */
    String ASYNC_POOL = "dreamAsyncThreadPool";

    /**
     * Zuul请求头TOKEN名称（不要有空格）
     */
    public static final String ZUUL_TOKEN_HEADER = "ZuulToken";
    /**
     * Zuul请求头TOKEN值
     */
    public static final String ZUUL_TOKEN_VALUE = "dream:zuul:123456";

    /**
     * gif类型
     */
    public static final String GIF = "gif";
    /**
     * png类型
     */
    public static final String PNG = "png";

    /**
     * 验证码 key前缀
     */
    public static final String CODE_PREFIX = "dream.captcha.";

    /**
     * 注册用户角色ID
     */
    Long REGISTER_ROLE_ID = 2L;
}
