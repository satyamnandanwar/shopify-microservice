package com.authservice.service;

import com.authservice.dto.AuthResponse;
import com.authservice.dto.LoginRequest;
import com.authservice.dto.RegisterRequest;
import com.authservice.entity.User;
import com.authservice.repository.UserRepository;
import com.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest request) {

        // 1. Check duplicates
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Email already exists");
        }

        // 2. Default role to USER if not provided
        String role = "USER";

        // 3. Save new user with BCrypt encoded password
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        // 4. Generate tokens
        return buildAuthResponse(user.getUsername(), user.getRole());
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        // 1. Find user — 401 if not found (never say "user not found" — security best practice)
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        // 2. Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        // 3. Generate tokens
        return buildAuthResponse(user.getUsername(), user.getRole());
    }

    @Override
    public AuthResponse refresh(String refreshToken) {

        // 1. Validate refresh token
        if (jwtUtil.isExpired(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Refresh token expired, please login again");
        }

        // 2. Extract username from refresh token
        String username = jwtUtil.getUsername(refreshToken);

        // 3. Load user to get current role
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "User not found"));

        // 4. Issue new access token only — refresh token stays the same
        String newAccessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)   // return the same refresh token
                .expiresIn(900)
                .role(user.getRole())
                .build();
    }

    // Private helper — builds full AuthResponse with both tokens
    private AuthResponse buildAuthResponse(String username, String role) {
        return AuthResponse.builder()
                .accessToken(jwtUtil.generateAccessToken(username, role))
                .refreshToken(jwtUtil.generateRefreshToken(username))
                .expiresIn(900)
                .role(role)
                .build();
    }
}