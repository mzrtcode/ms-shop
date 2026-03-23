package com.ecommerse.inventory_service.mapper;

import com.ecommerse.inventory_service.dto.InventoryRequestDTO;
import com.ecommerse.inventory_service.dto.InventoryResponseDTO;
import com.ecommerse.inventory_service.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(target = "id", ignore = true)
    Inventory toInventory(InventoryRequestDTO inventoryRequestDTO);

    @Mapping(target = "inStock", expression = "java(inventory.getQuantity() > 0 )")
    InventoryResponseDTO toInventoryResponse(Inventory inventory);
}
