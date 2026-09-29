package com.alan.product_api.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(description = "Estrutura padrão de erro da API")
public record ErrorResponse(

        @Schema(
                description = "Data e hora em que o erro ocorreu",
                example = "2026-09-28T20:30:00Z"
        )
        Instant timestamp,

        @Schema(
                description = "Código HTTP do erro",
                example = "404"
        )
        int status,

        @Schema(
                description = "Descrição do tipo do erro",
                example = "Product not Found"
        )
        String error,

        @Schema(
                description = "Mensagem descritiva do erro",
                example = "Product not found"
        )
        String message,

        @Schema(
                description = "Lista de erros específicos de validação"
        )
        List<FieldErrorResponse> errors
) {
}