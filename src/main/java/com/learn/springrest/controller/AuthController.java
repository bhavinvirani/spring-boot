package com.learn.springrest.controller;

import com.learn.springrest.dto.auth.LoginDTO;
import com.learn.springrest.dto.auth.RegisterDTO;
import com.learn.springrest.model.User;
import com.learn.springrest.service.AuthService;
import com.learn.springrest.utils.ApiResponse;
import com.learn.springrest.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    // Health check endpoint
    @GetMapping("/health-check")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(
                ApiResponse.success("Service is up and running 🚀")
        );
    }


    // Register  user
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody RegisterDTO registerDTO) {

        // Call the service to register the user
        User registeredUser = authService.register(registerDTO);
        System.out.println("Registered User: " + registeredUser.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success(registeredUser)
        );
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody LoginDTO loginDTO) {

        User authenticatedUser = authService.login(loginDTO);

        String jwtToken = jwtUtils.generateToken(authenticatedUser);

        return ResponseEntity.ok(
                ApiResponse.success(jwtToken)
        );

    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated User: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());
        return ResponseEntity.ok("Hello " + auth.getName());
    }

}
