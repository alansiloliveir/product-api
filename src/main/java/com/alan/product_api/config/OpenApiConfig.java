package com.alan.product_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product API",
                version = "1.0.0",
                description = "API REST para gerenciamento de produtos."
        )
)

public class OpenApiConfig {
}