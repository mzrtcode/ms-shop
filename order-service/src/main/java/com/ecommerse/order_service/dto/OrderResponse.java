package com.ecommerse.order_service.dto;

import java.util.List;

public record OrderResponse(
        String id,
        String orderNumber,
        List<OrderLineItemsResponse> orderLineItemsList)
{ }
