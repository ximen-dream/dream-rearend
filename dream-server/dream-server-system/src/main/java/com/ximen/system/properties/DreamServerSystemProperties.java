package com.ximen.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zhishun.cai
 * @date 2020/7/25 16:46
 * @note
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:dream-server-system.properties"})
@ConfigurationProperties(prefix = "dream.server.system")
public class DreamServerSystemProperties {
    /**
     * 免认证 URI，多个值的话以逗号分隔
     */
    private String anonUrl;

    private DreamSwaggerProperties swagger = new DreamSwaggerProperties();
}
