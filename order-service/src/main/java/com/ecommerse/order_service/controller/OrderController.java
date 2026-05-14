package com.ecommerse.order_service.controller;

import com.ecommerse.order_service.dto.OrderRequest;
import com.ecommerse.order_service.dto.OrderResponse;
import com.ecommerse.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class    OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CompletableFuture<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest orderRequest, @AuthenticationPrincipal Jwt jwt) {
        return orderService.placeOrder(orderRequest, jwt.getSubject());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<OrderResponse> getOrders(@AuthenticationPrincipal Jwt jwt) {

        String userId = jwt.getSubject();
        boolean isAdmin = false;

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        if(realmAccess != null && realmAccess.containsKey("roles")) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            isAdmin = roles.stream().anyMatch(role -> role.equalsIgnoreCase("ADMIN"));
        }


        return orderService.getOrders(userId, isAdmin);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    OrderResponse getOrderById(@PathVariable Long orderId){
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrderById(Long orderId){
        orderService.deleteOrderById(orderId);
    }
}
