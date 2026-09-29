package com.alan.product_api.controller;

import com.alan.product_api.dto.ProductRequest;
import com.alan.product_api.dto.ProductResponse;
import com.alan.product_api.exception.ResourceNotFoundException;
import com.alan.product_api.mapper.ProductMapper;
import com.alan.product_api.model.Product;
import com.alan.product_api.model.ProductStatus;
import com.alan.product_api.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ProductMapper productMapper;

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setName("Notebook Dell");
        request.setDescription("Notebook corporativo");
        request.setPrice(BigDecimal.valueOf(4500));

        Product product = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        // Simula o ID que seria gerado pelo banco após a persistência.
        ReflectionTestUtils.setField(product, "id", 1L);

        ProductResponse response = new ProductResponse(
                1L,
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        when(productMapper.toEntity(any(ProductRequest.class)))
                .thenReturn(product);

        when(productService.create(product))
                .thenReturn(product);

        when(productMapper.toResponse(product))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        "Location",
                        endsWith("/api/products/1")
                ))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Notebook Dell"))
                .andExpect(jsonPath("$.price").value(4500))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void shouldFindAllProducts() throws Exception {
        Product product = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        ProductResponse response = new ProductResponse(
                1L,
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        when(productService.findAll())
                .thenReturn(List.of(product));

        when(productMapper.toResponse(product))
                .thenReturn(response);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Notebook Dell"))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));
    }

    @Test
    void shouldFindProductById() throws Exception {
        Product product = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        ProductResponse response = new ProductResponse(
                1L,
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        when(productService.findById(1L))
                .thenReturn(product);

        when(productMapper.toResponse(product))
                .thenReturn(response);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Notebook Dell"));
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        when(productService.findById(999L))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Product not found: 999"
                        )
                );

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error")
                        .value("Resource not found"))
                .andExpect(jsonPath("$.message")
                        .value("Product not found: 999"));
    }

    @Test
    void shouldFindProductsByName() throws Exception {
        Product product = new Product(
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        ProductResponse response = new ProductResponse(
                1L,
                "Notebook Dell",
                "Notebook corporativo",
                BigDecimal.valueOf(4500),
                ProductStatus.ACTIVE
        );

        when(productService.findByName("Notebook"))
                .thenReturn(List.of(product));

        when(productMapper.toResponse(product))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/products/search")
                                .param("name", "Notebook")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Notebook Dell"));
    }

    @Test
    void shouldCountProducts() throws Exception {
        when(productService.count())
                .thenReturn(5L);

        mockMvc.perform(get("/api/products/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setName("Notebook Dell Updated");
        request.setDescription("Notebook atualizado");
        request.setPrice(BigDecimal.valueOf(4200));
        request.setStatus(ProductStatus.INACTIVE);

        Product product = new Product(
                "Notebook Dell Updated",
                "Notebook atualizado",
                BigDecimal.valueOf(4200),
                ProductStatus.INACTIVE
        );

        ProductResponse response = new ProductResponse(
                1L,
                "Notebook Dell Updated",
                "Notebook atualizado",
                BigDecimal.valueOf(4200),
                ProductStatus.INACTIVE
        );

        when(productMapper.toEntity(any(ProductRequest.class)))
                .thenReturn(product);

        when(productService.update(1L, product))
                .thenReturn(product);

        when(productMapper.toResponse(product))
                .thenReturn(response);

        mockMvc.perform(
                        put("/api/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("INACTIVE"))
                .andExpect(jsonPath("$.price").value(4200));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        doNothing()
                .when(productService)
                .delete(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService).delete(1L);
    }

    @Test
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        String invalidRequest = """
                {
                    "name": "",
                    "description": "Produto inválido",
                    "price": -10
                }
                """;

        mockMvc.perform(
                        post("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidRequest)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error")
                        .value("Validation failed"));
    }
}