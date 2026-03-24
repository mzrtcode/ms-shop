package com.ecommerse.inventory_service.controller;

import com.ecommerse.inventory_service.dto.InventoryRequestDTO;
import com.ecommerse.inventory_service.dto.InventoryResponseDTO;
import com.ecommerse.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String sku, @RequestParam Integer quantity){
        return inventoryService.isInStock(sku, quantity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponseDTO createInventory(@Valid @RequestBody InventoryRequestDTO inventoryRequest){
        return inventoryService.createInventory(inventoryRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponseDTO updateInventory(@PathVariable Long id, @RequestBody InventoryRequestDTO inventoryRequest){
         return inventoryService.updateInventory(id, inventoryRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDTO> getAllInventory(){
        return inventoryService.getAllInventory();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        inventoryService.deleteInventory(id);
    }

    @PutMapping("/reduce/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public void reduceStock(@PathVariable String sku, @RequestParam Integer quantity){
        inventoryService.reduceStock(sku, quantity);
    }


}
