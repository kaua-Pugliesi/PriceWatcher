package com.example.pricewatcher.controller;

import com.example.pricewatcher.Service.StoreService;
import com.example.pricewatcher.dto.request.CreateStoreRequestDto;
import com.example.pricewatcher.dto.request.UpdateStoreRequestDto;
import com.example.pricewatcher.dto.response.StoreResponseDto;
import com.example.pricewatcher.model.Store;
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
@RequestMapping("/api/stores")
@Tag(
        name = "Lojas",
        description = "Gerenciamento de lojas e marketplaces"
)
@SecurityRequirement(name = "bearerAuth")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @Operation(
            summary = "Criar loja",
            description = "Cadastra uma nova loja ou marketplace."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Loja criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados ou URL inválidos"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Loja já cadastrada"
            )
    })
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody @Valid CreateStoreRequestDto storeDto
    ) {
        Store store = new Store();

        store.setName(storeDto.name());
        store.setWebsiteUrl(storeDto.websiteUrl());

        UUID id = storeService.create(store);

        URI location = URI.create(
                "/api/stores/" + id
        );

        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Listar lojas",
            description = "Retorna todas as lojas cadastradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lojas retornadas com sucesso"
    )
    @GetMapping
    public ResponseEntity<List<StoreResponseDto>> findAll() {
        List<StoreResponseDto> stores =
                storeService.findAll()
                        .stream()
                        .map(this::toResponseDto)
                        .toList();

        return ResponseEntity.ok(stores);
    }

    @Operation(
            summary = "Buscar loja por ID",
            description = "Retorna uma loja específica."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Loja encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Loja não encontrada"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<StoreResponseDto> findById(
            @PathVariable UUID id
    ) {
        Store store = storeService.findById(id);

        return ResponseEntity.ok(toResponseDto(store));
    }

    @Operation(
            summary = "Atualizar loja",
            description = "Atualiza os dados de uma loja."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Loja atualizada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados ou URL inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Loja não encontrada"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Nome da loja já utilizado"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<StoreResponseDto> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateStoreRequestDto storeDto
    ) {
        Store store = new Store();

        store.setName(storeDto.name());
        store.setWebsiteUrl(storeDto.websiteUrl());

        Store updatedStore = storeService.update(id, store);

        return ResponseEntity.ok(toResponseDto(updatedStore));
    }

    @Operation(
            summary = "Excluir loja",
            description = "Exclui uma loja pelo ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Loja excluída com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Loja não encontrada"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {
        storeService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private StoreResponseDto toResponseDto(Store store) {
        return new StoreResponseDto(
                store.getId(),
                store.getName(),
                store.getWebsiteUrl()
        );
    }
}
