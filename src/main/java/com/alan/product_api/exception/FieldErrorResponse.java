package com.alan.product_api.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Erro de validação de um campo")
public record FieldErrorResponse(

        @Schema(
                description = "Nome do campo que apresentou erro",
                example = "price"
        )
        String field,

        @Schema(
                description = "Mensagem de validação",
                example = "must be greater than 0"
        )
        String message
) {
}