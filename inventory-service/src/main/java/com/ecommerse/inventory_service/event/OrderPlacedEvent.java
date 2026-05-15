package com.ecommerse.inventory_service.event;

import java.util.List;

public record OrderPlacedEvent(String orderNumber,
                               String email,
                               List<OrderItemEvent> items
                               ) {

    public record OrderItemEvent(String sku,
                                 String price,
                                 Integer quantity) { }
}
