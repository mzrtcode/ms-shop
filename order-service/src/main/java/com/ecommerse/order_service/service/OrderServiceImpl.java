package com.ecommerse.order_service.service;

import com.ecommerse.order_service.dto.OrderRequest;
import com.ecommerse.order_service.dto.OrderResponse;
import com.ecommerse.order_service.exception.ResourceNotFoundException;
import com.ecommerse.order_service.mapper.OrderMapper;
import com.ecommerse.order_service.model.Order;
import com.ecommerse.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        log.info("Colocando nuevo pedido");
        Order order = orderMapper.toOrder(orderRequest);
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
