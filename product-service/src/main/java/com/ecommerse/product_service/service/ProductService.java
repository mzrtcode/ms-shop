package com.ecommerse.product_service.service;

import com.ecommerse.product_service.dto.ProductRequestDTO;
import com.ecommerse.product_service.dto.ProductResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductResponseDTO create(ProductRequestDTO productRequestDTO);
    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(String productId);
    ProductResponseDTO updateProduct(String productId, ProductRequestDTO productRequest);
    void deleteProductById(String productId);
}
