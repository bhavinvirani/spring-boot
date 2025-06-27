package com.learn.springrest.controller;

import com.learn.springrest.service.AuthService;
import com.learn.springrest.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Health check endpoint
    @GetMapping("/health-check")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(
                ApiResponse.success("Service is up and running 🚀")
        );
    }

}
