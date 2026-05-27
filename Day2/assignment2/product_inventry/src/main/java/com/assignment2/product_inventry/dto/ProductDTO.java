package com.assignment2.product_inventry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private Double productPrice;
    private Integer productQuantity;
    private String productCategory;
}
