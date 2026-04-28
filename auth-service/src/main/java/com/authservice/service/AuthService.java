package com.authservice.service;

import com.authservice.dto.AuthResponse;
import com.authservice.dto.LoginRequest;
import com.authservice.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refresh(String refreshToken);
}