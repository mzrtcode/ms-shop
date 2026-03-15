package com.ecommerse.product_service.mapper;

import com.ecommerse.product_service.dto.ProductRequestDTO;
import com.ecommerse.product_service.dto.ProductResponseDTO;
import com.ecommerse.product_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toProduct(ProductRequestDTO productRequestDTO);

    ProductResponseDTO toProductResponseDTO(Product product);

    @Mapping(target = "id", ignore = true)
    void updateProductFromRequest(ProductRequestDTO productRequestDTO, @MappingTarget Product product);
}
