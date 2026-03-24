package com.ecommerse.inventory_service.service;


import com.ecommerse.inventory_service.dto.InventoryRequestDTO;
import com.ecommerse.inventory_service.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {

    boolean isInStock(String sku, Integer quantity);
    InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequest);
    List<InventoryResponseDTO> getAllInventory();
    InventoryResponseDTO getInventoryBySku(String sku);
    InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequest);
    void deleteInventory(Long id);
    void reduceStock(String sku, Integer quantity);
}
