package com.alan.product_api.mapper;

import com.alan.product_api.dto.ProductRequest;
import com.alan.product_api.dto.ProductResponse;
import com.alan.product_api.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        return new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStatus()
        );
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStatus()
        );
    }
}