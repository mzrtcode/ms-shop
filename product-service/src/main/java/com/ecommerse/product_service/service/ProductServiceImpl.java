package com.ecommerse.product_service.service;

import com.ecommerse.product_service.dto.ProductRequestDTO;
import com.ecommerse.product_service.dto.ProductResponseDTO;
import com.ecommerse.product_service.exception.ResourceNotFoundException;
import com.ecommerse.product_service.mapper.ProductMapper;
import com.ecommerse.product_service.model.Product;
import com.ecommerse.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {

        Product product = productRepository.save(productMapper.toProduct(productRequestDTO));

        log.info("Producto guardado: {}", product.getName());
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
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", productId));

        return productMapper.toProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(String productId, ProductRequestDTO productRequest) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", productId));

        productMapper.updateProductFromRequest(productRequest, product);
        Product updatedProduct = productRepository.save(product);

        log.info("Producto actualizado: {}", updatedProduct.getName());

        return productMapper.toProductResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProductById(String productId) {
        if(!productRepository.existsById(productId)) throw new RuntimeException("No existe el producto con el id: " + productId);
        productRepository.deleteById(productId);
        log.info("Producto con el id: {} fue eliminado", productId);

    }
}
