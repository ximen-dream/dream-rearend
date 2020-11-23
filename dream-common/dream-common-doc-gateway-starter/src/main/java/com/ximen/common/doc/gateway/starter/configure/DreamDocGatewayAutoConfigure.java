package com.ximen.common.doc.gateway.starter.configure;

import com.ximen.common.doc.gateway.starter.filter.DreamDocGatewayHeaderFilter;
import com.ximen.common.doc.gateway.starter.handler.DreamDocGatewayHandler;
import com.ximen.common.doc.gateway.starter.properties.DreamDocGatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;

/**
 * @author zhishun.cai
 * @date 2020/7/29 15:54
 * @note
 */
@Configuration
@EnableConfigurationProperties(DreamDocGatewayProperties.class)
@ConditionalOnProperty(value = "dream.doc.gateway.enable", havingValue = "true", matchIfMissing = true)
public class DreamDocGatewayAutoConfigure {
    private DreamDocGatewayProperties dreamDocGatewayProperties;
    private SecurityConfiguration securityConfiguration;
    private UiConfiguration uiConfiguration;


    public DreamDocGatewayAutoConfigure(DreamDocGatewayProperties dreamDocGatewayProperties) {
        this.dreamDocGatewayProperties = dreamDocGatewayProperties;
    }

    @Autowired(required = false)
    public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Autowired(required = false)
    public void setUiConfiguration(UiConfiguration uiConfiguration) {
        this.uiConfiguration = uiConfiguration;
    }


    @Bean
    public DreamDocGatewayResourceConfigure febsDocGatewayResourceConfigure(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
        return new DreamDocGatewayResourceConfigure(routeLocator, gatewayProperties);
    }

    @Bean
    public DreamDocGatewayHeaderFilter febsDocGatewayHeaderFilter() {
        return new DreamDocGatewayHeaderFilter();
    }

    @Bean
    public DreamDocGatewayHandler febsDocGatewayHandler(SwaggerResourcesProvider swaggerResources) {
        DreamDocGatewayHandler dreamDocGatewayHandler = new DreamDocGatewayHandler();
        dreamDocGatewayHandler.setSecurityConfiguration(securityConfiguration);
        dreamDocGatewayHandler.setUiConfiguration(uiConfiguration);
        dreamDocGatewayHandler.setSwaggerResources(swaggerResources);
        dreamDocGatewayHandler.setProperties(dreamDocGatewayProperties);
        return dreamDocGatewayHandler;
    }
}
