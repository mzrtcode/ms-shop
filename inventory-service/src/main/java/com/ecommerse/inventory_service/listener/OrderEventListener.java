package com.ecommerse.inventory_service.listener;

import com.ecommerse.inventory_service.event.OrderPlacedEvent;
import com.ecommerse.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {

    private final InventoryService inventoryService;
    private final String QUEUE_NAME = "inventory-queue";

    @RabbitListener(queues = QUEUE_NAME)
    public void handleOrderPlaceEvent(OrderPlacedEvent orderPlacedEvent) {
        orderPlacedEvent.items().forEach(item -> {
            try{
                inventoryService.reduceStock(item.sku(), item.quantity());
                log.info("Stock descontado para SKU: {}, cantidad: {}", item.sku(),  item.quantity());
            }catch (Exception ex){
                log.error("Error al descontar stock para SKU: {}: {}", item.sku(), ex.getMessage());
            }
        });
    }
}
