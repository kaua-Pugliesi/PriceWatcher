package com.example.pricewatcher.Service;

import com.example.pricewatcher.dto.request.LoginRequestDto;
import com.example.pricewatcher.dto.request.RegisterRequestDto;

public interface AuthService {
    public String login(LoginRequestDto request);
    public String register(RegisterRequestDto request);
}
