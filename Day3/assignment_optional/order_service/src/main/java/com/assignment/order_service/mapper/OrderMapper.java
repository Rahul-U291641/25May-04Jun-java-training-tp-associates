package com.assignment.order_service.mapper;

import com.assignment.order_service.dto.OrderResponse;
import com.assignment.order_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mappings({
            @Mapping(source = "id", target = "orderId"),
            @Mapping(source = "quantity", target = "netQuantity"),
            @Mapping(source = "price", target = "totalPrice"),
    })
    OrderResponse toDTO(Order order);

    @Mappings({
            @Mapping(source = "orderId", target = "id"),
            @Mapping(source = "netQuantity", target = "quantity"),
            @Mapping(source = "totalPrice", target = "price"),
    })
    Order toEntity(OrderResponse orderResponse);
}
