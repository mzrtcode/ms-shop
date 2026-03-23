package com.ecommerse.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank(message = "El nombre del producto no puede estar vacio")
        String name,
        String description,
        @NotNull
        @Positive(message = "El precio debe ser mayor a 0")
        BigDecimal price
) {
}
