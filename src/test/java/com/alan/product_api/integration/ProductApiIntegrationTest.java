package com.alan.product_api.integration;

import com.alan.product_api.dto.ProductRequest;
import com.alan.product_api.model.ProductStatus;
import com.alan.product_api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.postgresql.PostgreSQLContainer;
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ProductApiIntegrationTest.ContainersConfiguration.class)
class ProductApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void cleanDatabase() {
        productRepository.deleteAll();
    }

    @Test
    void shouldPersistAndRetrieveProductThroughApi() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setName("Notebook Integration");
        request.setDescription(
                "Produto criado no teste de integração"
        );
        request.setPrice(BigDecimal.valueOf(3500));

        String json = objectMapper.writeValueAsString(request);

        var result = mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name")
                        .value("Notebook Integration"))
                .andExpect(jsonPath("$.status")
                        .value("ACTIVE"))
                .andReturn();

        String location =
                result.getResponse().getHeader("Location");

        assertNotNull(location);

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Notebook Integration"))
                .andExpect(jsonPath("$.price")
                        .value(3500))
                .andExpect(jsonPath("$.status")
                        .value("ACTIVE"));

        assertEquals(1, productRepository.count());
    }

    @Test
    void shouldUpdateProductStatusThroughApi() throws Exception {
        ProductRequest createRequest = new ProductRequest();

        createRequest.setName("Notebook Status");
        createRequest.setDescription(
                "Teste de mudança de status"
        );
        createRequest.setPrice(BigDecimal.valueOf(3000));

        String createJson =
                objectMapper.writeValueAsString(createRequest);

        var createResult = mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson)
        )
                .andExpect(status().isCreated())
                .andReturn();

        String location =
                createResult.getResponse().getHeader("Location");

        assertNotNull(location);

        ProductRequest updateRequest = new ProductRequest();

        updateRequest.setName("Notebook Status");
        updateRequest.setDescription("Produto inativado");
        updateRequest.setPrice(BigDecimal.valueOf(3000));
        updateRequest.setStatus(ProductStatus.INACTIVE);

        String updateJson =
                objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(
                put(location)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status")
                        .value("INACTIVE"));

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status")
                        .value("INACTIVE"));
    }

    @TestConfiguration(proxyBeanMethods = false)
    static class ContainersConfiguration {

        @Bean
        @ServiceConnection
        PostgreSQLContainer postgresContainer() {
            return new PostgreSQLContainer("postgres:17-alpine");
        }
    }
}