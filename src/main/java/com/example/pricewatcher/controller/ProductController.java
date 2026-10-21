package com.example.pricewatcher.controller;

import com.example.pricewatcher.Service.ProductService;
import com.example.pricewatcher.dto.request.CreateProductRequestDto;
import com.example.pricewatcher.dto.request.UpdateProductRequestDto;
import com.example.pricewatcher.dto.response.ProductResponseDto;
import com.example.pricewatcher.model.Category;
import com.example.pricewatcher.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(
        name = "Produtos",
        description = "Gerenciamento de produtos"
)
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Criar produto",
            description = "Cadastra um produto associado a uma categoria."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto criado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados ou categoria inválidos"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Produto já cadastrado"
            )
    })
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody @Valid CreateProductRequestDto productDto
    ) {
        Product product = toEntity(productDto);

        UUID id = productService.create(product);

        URI location = URI.create(
                "/api/products/" + id
        );

        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Listar produtos",
            description = "Retorna todos os produtos cadastrados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Produtos retornados com sucesso"
    )
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAll() {
        List<ProductResponseDto> products =
                productService.findAll()
                        .stream()
                        .map(this::toResponseDto)
                        .toList();

        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna um produto específico."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(
            @PathVariable UUID id
    ) {
        Product product = productService.findById(id);

        return ResponseEntity.ok(toResponseDto(product));
    }

    @Operation(
            summary = "Listar produtos por categoria",
            description = "Retorna os produtos pertencentes a uma categoria."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produtos encontrados"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada"
            )
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDto>> findByCategory(
            @PathVariable UUID categoryId
    ) {
        List<ProductResponseDto> products =
                productService.findByCategory(categoryId)
                        .stream()
                        .map(this::toResponseDto)
                        .toList();

        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza os dados de um produto."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados ou categoria inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Nome do produto já utilizado"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateProductRequestDto productDto
    ) {
        Product product = toEntity(productDto);

        Product updatedProduct =
                productService.update(id, product);

        return ResponseEntity.ok(toResponseDto(updatedProduct));
    }

    @Operation(
            summary = "Excluir produto",
            description = "Exclui um produto pelo ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Produto excluído com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private Product toEntity(CreateProductRequestDto productDto) {
        Product product = new Product();

        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setImageUrl(productDto.imageUrl());

        Category category = new Category();
        category.setId(productDto.categoryId());

        product.setCategory(category);

        return product;
    }

    private Product toEntity(UpdateProductRequestDto productDto) {
        Product product = new Product();

        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setImageUrl(productDto.imageUrl());

        Category category = new Category();
        category.setId(productDto.categoryId());

        product.setCategory(category);

        return product;
    }

    private ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImageUrl(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }
}
