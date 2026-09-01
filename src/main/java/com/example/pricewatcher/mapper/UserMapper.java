package com.example.pricewatcher.mapper;

import com.example.pricewatcher.dto.request.CreateUserRequestDto;
import com.example.pricewatcher.dto.request.UpdateUserRequestDto;
import com.example.pricewatcher.dto.response.UserResponseDto;
import com.example.pricewatcher.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public User fromCreateUserRequestDtoToEntity(CreateUserRequestDto userDto){
        return new User(
                userDto.name(),
                userDto.email(),
                userDto.password()
        );
    }

    public User fromUpdateUserRequestDtoToEntity(UpdateUserRequestDto userDto){
        return new User(
                userDto.name(),
                userDto.email(),
                userDto.password()
        );
    }

    public UserResponseDto fromUserToDto(User user){
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
    }

    public List<UserResponseDto> fromUserToDto(List<User> users){
        return users.stream().map(this::fromUserToDto).toList();
    }

}
