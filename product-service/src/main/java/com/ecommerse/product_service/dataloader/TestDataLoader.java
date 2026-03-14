package com.ecommerse.product_service.dataloader;

import com.ecommerse.product_service.model.Product;
import com.ecommerse.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TestDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        Product product = Product.builder()
                .name("MacBook Air M5")
                .description("Laptop")
                .price(BigDecimal.valueOf(1000))
                .build();

        productRepository.save(product);

        IO.println("Datos de prueba cargados " + product.getName());
    }
}
