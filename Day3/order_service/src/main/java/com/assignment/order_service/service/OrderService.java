package com.assignment.order_service.service;

import com.assignment.order_service.dto.ApiResponse;
import com.assignment.order_service.dto.OrderResponse;
import com.assignment.order_service.dto.ProductResponse;
import com.assignment.order_service.dto.UserResponse;
import com.assignment.order_service.entity.Order;
import com.assignment.order_service.entity.Product;
import com.assignment.order_service.mapper.OrderMapper;
import com.assignment.order_service.mapper.ProductMapper;
import com.assignment.order_service.utils.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RestClient restClient;

    public ApiResponse<OrderResponse> getOrderById(String orderId) {
        // Mocking an order response for demonstration purposes
        OrderResponse orderResponse;
        try {
            Order order = getDummyOrderById(orderId);
            ProductResponse productResponse = getRelevantProduct(order.getProductId());
            UserResponse userResponse = getRelevantUser(order.getUserId());

            orderResponse = orderMapper.toDTO(order);
            orderResponse.setProduct(productResponse);
            orderResponse.setUser(userResponse);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Failed to retrieve order: " + e.getMessage(), null);
        }

        return new ApiResponse<>(true, "Order retrieved successfully", orderResponse);
    }

    private UserResponse getRelevantUser(String userId) {
        UserResponse userResponse;
        try {
            // Call the User Service to fetch user details using RestClient
            String userServiceUrl = "http://localhost:8080/api/users/" + userId;
            userResponse = restClient.get(userServiceUrl, UserResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user information: " + e.getMessage());
        }

        return userResponse;
    }

    private ProductResponse getRelevantProduct(String productId) {
        // Fetch product information by calling the Product Service, similar to the User API call used to retrieve user details        Product product = new Product();
        Product product = new Product();
        product.setId(productId);
        product.setName("Sample Product A");
        product.setPrice(24.99);

        // Using MapStrut converted Entity object to a DTO
        return productMapper.toDTO(product);
    }

    private Order getDummyOrderById(String orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUserId("user123");
        order.setProductId("product123");
        order.setQuantity(2);
        order.setPrice(49.99);
        return order;
    }
}
