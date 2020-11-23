package com.ximen.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zhishun.cai
 * @date 2020/7/24 14:38
 * @note
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:dream-auth.properties"})
@ConfigurationProperties(prefix = "dream.auth")
public class DreamAuthProperties {

    //因为一个认证服务器可以根据多种Client来发放对应的令牌，所以这个属性使用的是数组形式
    private DreamClientsProperties[] clients = {};
    //用于指定access_token的有效时间 ;默认值为60 * 60 * 24秒
    private int accessTokenValiditySeconds = 60 * 60 * 24;
    //用于指定refresh_token的有效时间，默认值为60 * 60 * 24 * 7秒
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

    // 免认证路径
    private String anonUrl;

    //验证码配置类
    private DreamValidateCodeProperties code = new DreamValidateCodeProperties();

    /**
     * 是否使用 JWT令牌
     */
    private Boolean enableJwt;

    /**
     * JWT加签密钥
     */
    private String jwtAccessKey;

    /**
     * 社交登录所使用的 Client
     */
    private String socialLoginClientId;
}
