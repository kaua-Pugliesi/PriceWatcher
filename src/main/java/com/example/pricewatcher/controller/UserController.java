package com.example.pricewatcher.controller;

import com.example.pricewatcher.Service.UserService;
import com.example.pricewatcher.dto.request.CreateUserRequestDto;
import com.example.pricewatcher.dto.request.UpdateUserRequestDto;
import com.example.pricewatcher.dto.response.UserResponseDto;
import com.example.pricewatcher.mapper.UserMapper;
import com.example.pricewatcher.model.User;
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
@RequestMapping("/api/users")
@Tag(
        name = "Usuários",
        description = "Gerenciamento de usuários"
)
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(
            UserService userService,
            UserMapper userMapper
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "E-mail já cadastrado"
            )
    })
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody @Valid CreateUserRequestDto userDto
    ) {
        User user =
                userMapper.fromCreateUserRequestDtoToEntity(userDto);

        UUID uuid = userService.create(user);

        URI location = URI.create(
                "/users/" + uuid
        );

        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Listar usuários",
            description = "Retorna todos os usuários cadastrados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Usuários retornados com sucesso"
    )
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> list() {
        List<User> users = userService.findAll();

        List<UserResponseDto> userResponseDtos =
                userMapper.fromUserToDto(users);

        return ResponseEntity.ok().body(userResponseDtos);
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário específico."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(
            @PathVariable UUID id
    ) {
        User user = userService.findById(id);

        UserResponseDto userResponseDto =
                userMapper.fromUserToDto(user);

        return ResponseEntity.ok().body(userResponseDto);
    }

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateUserRequestDto userDto
    ) {
        User user =
                userMapper.fromUpdateUserRequestDtoToEntity(userDto);

        User updated = userService.update(user, id);

        UserResponseDto userResponseDto =
                userMapper.fromUserToDto(updated);

        return ResponseEntity.ok().body(userResponseDto);
    }

    @Operation(
            summary = "Excluir usuário",
            description = "Exclui um usuário pelo ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário excluído com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
