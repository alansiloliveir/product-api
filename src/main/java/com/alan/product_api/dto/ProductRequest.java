package com.alan.product_api.dto;

import com.alan.product_api.model.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductRequest {

    @Schema(
            description = "Nome do produto",
            example = "Notebook Dell"
    )
    @NotBlank
    @Size(max = 100)
    private String name;

    @Schema(
            description = "Descrição do produto",
            example = "Notebook Dell Inspiron 15"
    )
    @Size(max = 500)
    private String description;

    @Schema(
            description = "Preço do produto",
            example = "4999.90"
    )
    @NotNull
    @Positive
    private BigDecimal price;

    @Schema(
            description = "Status inicial do produto. Quando não informado, será ACTIVE.",
            example = "ACTIVE",
            nullable = true
    )
    private ProductStatus status;

    public ProductRequest() {
    }

    public ProductRequest(
            String name,
            String description,
            BigDecimal price,
            ProductStatus status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}