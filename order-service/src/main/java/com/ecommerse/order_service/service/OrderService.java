package com.ecommerse.order_service.service;

import com.ecommerse.order_service.dto.OrderRequest;
import com.ecommerse.order_service.dto.OrderResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest orderRequest, String userId);
    //List<OrderResponse> getAllOrders();
    List<OrderResponse> getOrders(String userId, boolean isAdmin);
    OrderResponse getOrderById(Long orderId);
    void deleteOrderById(Long orderId);


}
