package com.assignment.order_service.mapper;

import com.assignment.order_service.dto.ProductResponse;
import com.assignment.order_service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "id", target = "productId"),
            @Mapping(source = "name", target = "productName"),
    })
    ProductResponse toDTO(Product product);

    @Mappings({
            @Mapping(source = "productId", target = "id"),
            @Mapping(source = "productName", target = "name"),
    })
    Product toEntity(ProductResponse productResponse);
}
