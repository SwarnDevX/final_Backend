package com.buildledger.iam.controller;

import com.buildledger.iam.dto.request.LoginRequestDTO;
import com.buildledger.iam.dto.response.ApiResponseDTO;
import com.buildledger.iam.dto.response.LoginResponseDTO;
import com.buildledger.iam.dto.response.UserResponseDTO;
import com.buildledger.iam.service.AuthService;
import com.buildledger.iam.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Login – returns JWT token [PUBLIC]")
    public ResponseEntity<ApiResponseDTO<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(
            ApiResponseDTO.success("Login successful", authService.login(request))
        );
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user info [AUTHENTICATED]")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getCurrentUser(Authentication auth) {
        return ResponseEntity.ok(
                ApiResponseDTO.success("Current user info retrieved successfully", userService.getUserByUsername(auth.getName()))
        );
    }
}

