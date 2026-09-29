package com.alan.product_api.controller;

import com.alan.product_api.dto.ProductRequest;
import com.alan.product_api.dto.ProductResponse;
import com.alan.product_api.exception.ErrorResponse;
import com.alan.product_api.mapper.ProductMapper;
import com.alan.product_api.model.Product;
import com.alan.product_api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(
        name = "Products",
        description = "Endpoints para gerenciamento de produtos"
)
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(
            ProductService productService,
            ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Operation(
            summary = "Cria um novo produto",
            description = "Cria um produto e define o status como ACTIVE quando não informado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ProductResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductRequest request) {

        Product product = productMapper.toEntity(request);

        Product createdProduct = productService.create(product);

        ProductResponse response =
                productMapper.toResponse(createdProduct);

        URI location =
                URI.create("/api/products/" + createdProduct.getId());

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @Operation(
            summary = "Lista todos os produtos",
            description = "Retorna todos os produtos cadastrados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produtos encontrados",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = ProductResponse.class
                                    )
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {

        List<ProductResponse> products = productService.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();

        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Busca um produto por ID",
            description = "Retorna os dados de um produto existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ProductResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable Long id) {

        Product product = productService.findById(id);

        return ResponseEntity.ok(
                productMapper.toResponse(product)
        );
    }

    @Operation(
            summary = "Busca produtos por nome",
            description = "Retorna os produtos cujo nome contém o texto informado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Busca realizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = ProductResponse.class
                                    )
                            )
                    )
            )
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> findByName(
            @RequestParam String name) {

        List<ProductResponse> products = productService.findByName(name)
                .stream()
                .map(productMapper::toResponse)
                .toList();

        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Conta os produtos",
            description = "Retorna a quantidade total de produtos cadastrados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contagem realizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = Long.class
                            )
                    )
            )
    })
    @GetMapping("/count")
    public ResponseEntity<Long> count() {

        return ResponseEntity.ok(productService.count());
    }

    @Operation(
            summary = "Atualiza um produto",
            description = "Atualiza os dados de um produto existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ProductResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        Product product = productMapper.toEntity(request);

        Product updatedProduct =
                productService.update(id, product);

        return ResponseEntity.ok(
                productMapper.toResponse(updatedProduct)
        );
    }

    @Operation(
            summary = "Remove um produto",
            description = "Remove permanentemente um produto pelo ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Produto removido com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}