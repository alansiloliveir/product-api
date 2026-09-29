package com.alan.product_api.service;

import com.alan.product_api.exception.ResourceNotFoundException;
import com.alan.product_api.model.Product;
import com.alan.product_api.model.ProductStatus;
import com.alan.product_api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Test
    void shouldCreateProductAsActiveWhenStatusIsNotProvided() {
        Product product = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                null
        );

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.create(product);

        assertEquals(ProductStatus.ACTIVE, result.getStatus());
        verify(productRepository).save(product);
    }

    @Test
    void shouldCreateProductKeepingProvidedStatus() {
        Product product = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.INACTIVE
        );

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.create(product);

        assertEquals(ProductStatus.INACTIVE, result.getStatus());
        verify(productRepository).save(product);
    }

    @Test
    void shouldRejectProductWithInvalidPrice() {
        Product product = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(-10),
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> productService.create(product)
        );

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldFindProductById() {
        Product product = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = productService.findById(1L);

        assertEquals(product, result);
        verify(productRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.findById(999L)
        );

        assertEquals("Product not found: 999", exception.getMessage());
        verify(productRepository).findById(999L);
    }

    @Test
    void shouldFindAllProducts() {
        Product product1 = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        Product product2 = new Product(
                "Mouse Logitech",
                "Mouse sem fio",
                BigDecimal.valueOf(150),
                ProductStatus.ACTIVE
        );

        when(productRepository.findAll())
                .thenReturn(List.of(product1, product2));

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));

        verify(productRepository).findAll();
    }

    @Test
    void shouldFindProductsByName() {
        Product product = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        when(productRepository.findByNameContainingIgnoreCase("notebook"))
                .thenReturn(List.of(product));

        List<Product> result = productService.findByName("notebook");

        assertEquals(1, result.size());
        assertEquals(product, result.get(0));

        verify(productRepository)
                .findByNameContainingIgnoreCase("notebook");
    }

    @Test
    void shouldCountProducts() {
        when(productRepository.count()).thenReturn(5L);

        long result = productService.count();

        assertEquals(5L, result);
        verify(productRepository).count();
    }

    @Test
    void shouldUpdateProduct() {
        Product existingProduct = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        Product updateData = new Product(
                "Notebook Dell Updated",
                "Notebook atualizado",
                BigDecimal.valueOf(4200),
                ProductStatus.INACTIVE
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(existingProduct));

        when(productRepository.save(existingProduct))
                .thenReturn(existingProduct);

        Product result = productService.update(1L, updateData);

        assertEquals("Notebook Dell Updated", result.getName());
        assertEquals("Notebook atualizado", result.getDescription());
        assertEquals(
                BigDecimal.valueOf(4200),
                result.getPrice()
        );
        assertEquals(
                ProductStatus.INACTIVE,
                result.getStatus()
        );

        verify(productRepository).findById(1L);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentProduct() {
        Product updateData = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.update(999L, updateData)
        );

        assertEquals("Product not found: 999", exception.getMessage());

        verify(productRepository).findById(999L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldDeleteProduct() {
        Product product = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.delete(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).delete(product);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentProduct() {
        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.delete(999L)
        );

        assertEquals("Product not found: 999", exception.getMessage());

        verify(productRepository).findById(999L);
        verify(productRepository, never()).delete(any(Product.class));
    }
}