package com.ecommerse.order_service.service;

import com.ecommerse.order_service.dto.OrderRequest;
import com.ecommerse.order_service.dto.OrderResponse;
import com.ecommerse.order_service.exception.ResourceNotFoundException;
import com.ecommerse.order_service.mapper.OrderMapper;
import com.ecommerse.order_service.model.Order;
import com.ecommerse.order_service.repository.OrderRepository;
import com.ecommerse.order_service.service.client.InventoryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@RefreshScope
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    //private final WebClient.Builder webClient;
    private final InventoryClient inventoryClient;

    @Value("${order.enabled:true}")
    private boolean ordersEnabled;


    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        if(!ordersEnabled){
            log.warn("Pedido rechazado: Servicio desabilidado por configuracion");
            throw new RuntimeException("El servicio de pedidos esta actualmente en mantenimiento. Intente mas tarde");
        }

        log.info("Colocando nuevo pedido");
        Order order = orderMapper.toOrder(orderRequest);

        for(var item : order.getOrderLineItemsList()){
            String sku = item.getSku();
            Integer quantity = item.getQuantity();

            try {
                inventoryClient.reduceStock(sku, quantity);
              /*  webClient.build()
                        .put()
                        .uri("http://localhost:8082/api/v1/inventory/reduce/" + sku,
                                uriBuilder -> uriBuilder.queryParam("quantity", quantity).build())
                        .retrieve()
                        .bodyToMono(Boolean.class)
                       .block(); */
            }catch (Exception e){
                log.error("Error al reducir stock para el producto {}: {}", sku, e.getMessage());
                throw new IllegalArgumentException("No se pudo procesar la orden: Stock insuficiente/error de inventario");
            }


        }
        order.setOrderNumber(UUID.randomUUID().toString());

        Order savedOrder = orderRepository.save(order);
        log.info("Order guradada con exito. ID: {}", savedOrder.getId());
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId));
    }

    @Override
    @Transactional
    public void deleteOrderById(Long orderId) {
        if(!orderRepository.existsById(orderId)) throw new ResourceNotFoundException("Order", "Id", orderId);

        orderRepository.deleteById(orderId);
        log.info("Order eliminada. ID: {}", orderId);
    }
}
