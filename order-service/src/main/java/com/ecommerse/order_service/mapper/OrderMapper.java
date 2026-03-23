package com.ecommerse.order_service.mapper;

import com.ecommerse.order_service.dto.OrderLineItemsRequest;
import com.ecommerse.order_service.dto.OrderLineItemsResponse;
import com.ecommerse.order_service.dto.OrderRequest;
import com.ecommerse.order_service.dto.OrderResponse;
import com.ecommerse.order_service.model.Order;
import com.ecommerse.order_service.model.OrderLineItems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder (OrderRequest orderRequest);
    OrderLineItems toOrderLineItems(OrderLineItemsRequest orderLineItemsRequest);
    OrderResponse toOrderResponse(Order order);
    OrderLineItemsResponse toOrderLineItemsResponse(OrderLineItems orderLineItems);
}
