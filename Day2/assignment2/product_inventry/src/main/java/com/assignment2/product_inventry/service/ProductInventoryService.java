package com.assignment2.product_inventry.service;

import com.assignment2.product_inventry.dto.ApiResponse;
import com.assignment2.product_inventry.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInventoryService {
    private static final Logger log = LoggerFactory.getLogger(ProductInventoryService.class);

    public ApiResponse<ProductDTO> addProduct(ProductDTO productDTO) {
        Objects.requireNonNull(productDTO, "productDTO must not be null");

        Optional<ApiResponse<ProductDTO>> validation = validate(productDTO);
        if (validation.isPresent()) {
            log.debug("Product validation failed: {}", validation.get().getMessage());
            return validation.get();
        }

        // TODO: persist using a repository; for now return success with the DTO
        log.debug("Product validated successfully: {}", productDTO.getProductName());
        return new ApiResponse<>(true, "Product added successfully", productDTO);
    }

    // Validate product fields and return an error response if invalid
    private Optional<ApiResponse<ProductDTO>> validate(ProductDTO productDTO) {
        if (productDTO.getProductPrice() == null || productDTO.getProductPrice() <= 0) {
            return Optional.of(new ApiResponse<>(false, "Invalid product price", null));
        }
        if (productDTO.getProductQuantity() == null || productDTO.getProductQuantity() < 0) {
            return Optional.of(new ApiResponse<>(false, "Invalid product quantity", null));
        }
        return Optional.empty();
    }

    public List<ProductDTO> getAllProducts() {
        // Return a small sample list for now; switch to a repository-backed call later.
        return List.of(
                new ProductDTO(1L, "Product 1", 10.0, 100, "Category A"),
                new ProductDTO(2L, "Product 2", 20.0, 200, "Category B"),
                new ProductDTO(3L, "Product 3", 30.0, 300, "Category C"),
                new ProductDTO(4L, "Product 4", 40.0, 400, "Category D")
        );
    }

    public String updateProduct(Long id) {
        return "Product with ID " + id + " updated successfully.";
    }

    public String deleteProduct(Long id) {
        return "Product with ID " + id + " deleted successfully.";
    }
}
