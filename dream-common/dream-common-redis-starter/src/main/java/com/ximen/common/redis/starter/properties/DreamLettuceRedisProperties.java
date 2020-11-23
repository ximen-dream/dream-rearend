package com.ximen.common.redis.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhishun.cai
 * @date 2020/7/29 19:35
 * @note
 */
@ConfigurationProperties(prefix = "dream.lettuce.redis")
public class DreamLettuceRedisProperties {

    /**
     * 是否开启Lettuce Redis
     */
    private Boolean enable = true;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "FebsLettuceRedisProperties{" +
                "enable=" + enable +
                '}';
    }
}
