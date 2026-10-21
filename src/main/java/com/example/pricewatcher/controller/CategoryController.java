package com.example.pricewatcher.controller;

import com.example.pricewatcher.Service.CategoryService;
import com.example.pricewatcher.dto.request.CreateCategoryRequestDto;
import com.example.pricewatcher.dto.request.UpdateCategoryRequestDto;
import com.example.pricewatcher.dto.response.CategoryResponseDto;
import com.example.pricewatcher.model.Category;
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
@RequestMapping("/api/categories")
@Tag(
        name = "Categorias",
        description = "Gerenciamento de categorias"
)
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Criar categoria",
            description = "Cadastra uma nova categoria."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Categoria criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Categoria já cadastrada"
            )
    })
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody @Valid CreateCategoryRequestDto categoryDto
    ) {
        Category category = new Category();

        category.setName(categoryDto.name());
        category.setDescription(categoryDto.description());

        UUID id = categoryService.create(category);

        URI location = URI.create(
                "/api/categories/" + id
        );

        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Listar categorias",
            description = "Retorna todas as categorias cadastradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Categorias retornadas com sucesso"
    )
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAll() {
        List<CategoryResponseDto> categories =
                categoryService.findAll()
                        .stream()
                        .map(this::toResponseDto)
                        .toList();

        return ResponseEntity.ok(categories);
    }

    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna uma categoria específica."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> findById(
            @PathVariable UUID id
    ) {
        Category category = categoryService.findById(id);

        return ResponseEntity.ok(toResponseDto(category));
    }

    @Operation(
            summary = "Atualizar categoria",
            description = "Atualiza os dados de uma categoria."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria atualizada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Nome da categoria já utilizado"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateCategoryRequestDto categoryDto
    ) {
        Category category = new Category();

        category.setName(categoryDto.name());
        category.setDescription(categoryDto.description());

        Category updatedCategory =
                categoryService.update(id, category);

        return ResponseEntity.ok(toResponseDto(updatedCategory));
    }

    @Operation(
            summary = "Excluir categoria",
            description = "Exclui uma categoria pelo ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Categoria excluída com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {
        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private CategoryResponseDto toResponseDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
