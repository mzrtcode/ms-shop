package com.ecommerse.order_service.config;

import com.ecommerse.order_service.service.client.InventoryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient(){
        return WebClient.builder();
    }

    @Bean
    public InventoryClient inventoryClient(WebClient.Builder builder){

        WebClient webclient = builder
                .baseUrl("http://INVENTORY-SERVICE")
                .build();

        // Crea una fábrica que generará clientes HTTP basados en interfaces
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webclient)).build();

        // Crea una implementación de InventoryClient usando esa fábrica
        return factory.createClient(InventoryClient.class);
    }
}
