package com.learn.springrest.service;

import com.learn.springrest.dto.auth.AuthenticationResponseDTO;
import com.learn.springrest.dto.auth.LoginDTO;
import com.learn.springrest.dto.auth.RegisterDTO;
import com.learn.springrest.model.User;

public interface AuthService {

    // Register
    User register(RegisterDTO user);

    // Login
    User login(LoginDTO input);

}
