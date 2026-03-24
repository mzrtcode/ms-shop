package com.ecommerse.order_service.config;

import com.ecommerse.order_service.service.client.InventoryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
    }

    @Bean
    public InventoryClient inventoryClient(WebClient webClient){
        // Convierte WebClient en un adaptador para llamadas HTTP
        WebClientAdapter adapter = WebClientAdapter.create(webClient);

        // Crea una fábrica que generará clientes HTTP basados en interfaces
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        // Crea una implementación de InventoryClient usando esa fábrica
        return factory.createClient(InventoryClient.class);
    }
}
