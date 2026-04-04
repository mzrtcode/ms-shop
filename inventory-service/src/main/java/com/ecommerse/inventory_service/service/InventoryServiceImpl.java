package com.ecommerse.inventory_service.service;

import com.ecommerse.inventory_service.dto.InventoryRequestDTO;
import com.ecommerse.inventory_service.dto.InventoryResponseDTO;
import com.ecommerse.inventory_service.exception.ResourceNotFoundException;
import com.ecommerse.inventory_service.mapper.InventoryMapper;
import com.ecommerse.inventory_service.model.Inventory;
import com.ecommerse.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@RefreshScope
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Value("${inventory.allow-backorders:false}")
    private boolean allowBackOrders;

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String sku, Integer quantity) {

        if(allowBackOrders){
            log.warn("MODO BACKORDER ACTIVO: Autorizando stock para SKU: {}", sku);
            return true;
        }

        Optional<Inventory> bySku = inventoryRepository.findBySku(sku);
        return bySku.map(inventory -> inventory.getQuantity() >= quantity)
                .orElse(false);
    }

    @Override
    @Transactional
    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequest) {

        boolean existsBySku = inventoryRepository.existsBySku(inventoryRequest.sku());
        if(existsBySku) throw new RuntimeException("El inventario para el SKU " + inventoryRequest.sku() + " ya existe");

        Inventory inventory = inventoryMapper.toInventory(inventoryRequest);
        Inventory savedInventory = inventoryRepository.save(inventory);

        log.info("Inventario creado para el SKU: {}", savedInventory.getSku());
        return  inventoryMapper.toInventoryResponse(savedInventory);
    }

    @Override
    public List<InventoryResponseDTO> getAllInventory() {
        List<Inventory> all = inventoryRepository.findAll();

        return all.stream().map(inventoryMapper::toInventoryResponse)
                .toList();
    }

    @Override
    public InventoryResponseDTO getInventoryBySku(String sku) {
        return inventoryRepository.findBySku(sku)
                .map(inventoryMapper::toInventoryResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", "sku", sku));
    }

    @Override
    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequest) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", "id", id));

        inventory.setSku(inventoryRequest.sku());
        inventory.setQuantity(inventoryRequest.quantity());
        Inventory savedInventory = inventoryRepository.save(inventory);
        log.info("Inventario actualizado para el ID:{}", id);

        return  inventoryMapper.toInventoryResponse(savedInventory);
    }

    @Override
    public void deleteInventory(Long id) {
        if(!inventoryRepository.existsById(id)) throw new ResourceNotFoundException("Inventario", "id", id);

        inventoryRepository.deleteById(id);
        log.info("Inventario eliminado para el ID:{}", id);
    }

    @Override
    @Transactional
    public void reduceStock(String sku, Integer quantity) {
        var inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + sku));


        Integer currentQuantity = inventory.getQuantity();
        if(currentQuantity < quantity) throw new RuntimeException("Stock insuficiente para : " + sku);
        inventory.setQuantity(currentQuantity - quantity);
        inventoryRepository.save(inventory);

    }
}
