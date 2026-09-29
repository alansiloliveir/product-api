package com.alan.product_api.dto;

import com.alan.product_api.model.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductResponse(

        @Schema(
                description = "Identificador único do produto",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nome do produto",
                example = "Notebook Dell"
        )
        String name,

        @Schema(
                description = "Descrição do produto",
                example = "Notebook Dell Inspiron 15"
        )
        String description,

        @Schema(
                description = "Preço do produto",
                example = "4999.90"
        )
        BigDecimal price,

        @Schema(
                description = "Status atual do produto",
                example = "ACTIVE"
        )
        ProductStatus status
) {
}