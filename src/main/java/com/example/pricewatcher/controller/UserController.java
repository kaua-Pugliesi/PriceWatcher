package com.example.pricewatcher.controller;

import com.example.pricewatcher.Service.UserService;
import com.example.pricewatcher.dto.request.CreateUserRequestDto;
import com.example.pricewatcher.dto.request.UpdateUserRequestDto;
import com.example.pricewatcher.dto.response.UserResponseDto;
import com.example.pricewatcher.mapper.UserMapper;
import com.example.pricewatcher.model.User;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper ) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateUserRequestDto userDto){
        User user = userMapper.fromCreateUserRequestDtoToEntity(userDto);
        UUID uuid = userService.create(user);
        URI location = URI.create(
                "/users/" + uuid);

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> list(){
        List<User> users = userService.findAll();
        List<UserResponseDto> userResponseDtos = userMapper.fromUserToDto(users);
        return ResponseEntity.ok().body(userResponseDtos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id){
        User user = userService.findById(id);
        UserResponseDto userResponseDto = userMapper.fromUserToDto(user);
        return ResponseEntity.ok().body(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id, @RequestBody @Valid UpdateUserRequestDto userDto){
        User user = userMapper.fromUpdateUserRequestDtoToEntity(userDto);
        User updated = userService.update(user, id);
        UserResponseDto userResponseDto = userMapper.fromUserToDto(updated);
        return ResponseEntity.ok().body(userResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
