package org.smartframework.cloud.examples.api.ac.core.configure;

import org.smartframework.cloud.examples.api.ac.core.controller.ApiMetaController;
import org.smartframework.cloud.examples.api.ac.core.listener.NotifyGatewayFetchApiMetaListener;
import org.smartframework.cloud.examples.api.ac.core.properties.ApiAcProperties;
import org.smartframework.cloud.examples.support.rpc.gateway.ApiMetaRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@EnableConfigurationProperties(ApiAcProperties.class)
public class ApiAcAutoConfigure {

    @Autowired
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public NotifyGatewayFetchApiMetaListener notifyGatewayFetchApiMetaListener(ApiMetaRpc apiMetaRpc, ApiAcProperties apiAcProperties) {
        return new NotifyGatewayFetchApiMetaListener(apiMetaRpc, apiAcProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiMetaController apiMetaController() {
        return new ApiMetaController();
    }

}