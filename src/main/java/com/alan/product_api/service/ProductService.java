package com.alan.product_api.service;

import com.alan.product_api.mapper.ProductMapper;
import com.alan.product_api.model.Product;
import com.alan.product_api.model.ProductStatus;
import com.alan.product_api.repository.ProductRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.alan.product_api.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        validatePrice(product.getPrice());

        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.ACTIVE);
        }

        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found: " + id));
    }

    public List<Product> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public long count() {
        return productRepository.count();
    }

    public Product update(Long id, Product product) {
        validatePrice(product.getPrice());

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found: " + id));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());

        if (product.getStatus() != null) {
            existingProduct.setStatus(product.getStatus());
        }

        return productRepository.save(existingProduct);
    }

    public void delete(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found: " + id));

        productRepository.delete(existingProduct);
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Product price must be greater than zero");
        }
    }
}