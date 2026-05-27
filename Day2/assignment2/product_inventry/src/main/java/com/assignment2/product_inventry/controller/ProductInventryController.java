package com.assignment2.product_inventry.controller;

import com.assignment2.product_inventry.dto.ApiResponse;
import com.assignment2.product_inventry.dto.ProductDTO;
import com.assignment2.product_inventry.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductInventryController {

    @Autowired
    private ProductInventoryService productInventoryService;

    @PostMapping
    public ApiResponse<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
       return productInventoryService.addProduct(productDTO);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productInventoryService.getAllProducts();
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id) {
        return productInventoryService.updateProduct(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productInventoryService.deleteProduct(id);
    }

}
