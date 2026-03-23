package com.ecommerse.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record InventoryRequestDTO(@NotBlank
                               String sku,
                                  @Min(0)
                               Integer quantity){}
