package com.ecommerse.product_service.service;

import com.ecommerse.product_service.dto.ProductRequestDTO;
import com.ecommerse.product_service.dto.ProductResponseDTO;
import com.ecommerse.product_service.mapper.ProductMapper;
import com.ecommerse.product_service.model.Product;
import com.ecommerse.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {

        Product product = productRepository.save(productMapper.toProduct(productRequestDTO));
        return productMapper.toProductResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(String productId) {

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el id: " + productId));

        return productMapper.toProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(String productId, ProductRequestDTO productRequest) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el id: " + productId));

        productMapper.updateProductFromRequest(productRequest, product);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toProductResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProductById(String productId) {
        if(!productRepository.existsById(productId)) throw new RuntimeException("No existe el producto con el id: " + productId);
        productRepository.deleteById(productId);
    }
}
