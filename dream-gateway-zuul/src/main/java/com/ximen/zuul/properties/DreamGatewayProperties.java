package com.ximen.zuul.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zhishun.cai
 * @date 2020/7/25 23:44
 * @note
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:dream-gateway.properties"})
@ConfigurationProperties(prefix = "dream.gateway")
public class DreamGatewayProperties {
    /**
     * 禁止外部访问的 URI，多个值的话以逗号分隔
     */
    private String forbidRequestUri;
}
