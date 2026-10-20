package com.example.pricewatcher.Service.impl;

import com.example.pricewatcher.Service.AuthService;
import com.example.pricewatcher.dto.request.LoginRequestDto;
import com.example.pricewatcher.dto.request.RegisterRequestDto;
import com.example.pricewatcher.exception.EmailAlreadyExistsException;
import com.example.pricewatcher.model.User;
import com.example.pricewatcher.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String login(LoginRequestDto request) {

        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.email(),
                  request.password()
          )
        );

        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow();

        return jwtService.generateToken(user);
    }

    @Override
    public String register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        User savedUser = userRepository.save(user);

        return jwtService.generateToken(savedUser);
    }
}
