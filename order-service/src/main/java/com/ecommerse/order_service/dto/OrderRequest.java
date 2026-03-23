package com.ecommerse.order_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(
        @NotEmpty(message = "La orden debe contener al menos un item")
        @Valid //Importante validar el JSON anidado
        List<OrderLineItemsRequest> orderLineItemsList)
{ }
