package com.ecommerse.inventory_service.dto;

public record InventoryResponseDTO(Long id,
                                   String sku,
                                   Integer quantity,
                                   Boolean inStock){

}
