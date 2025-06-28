package com.learn.springrest.service;


import com.learn.springrest.dto.auth.LoginDTO;
import com.learn.springrest.dto.auth.RegisterDTO;
import com.learn.springrest.exception.EmailAlreadyExistsException;
import com.learn.springrest.exception.ResourceNotFoundException;
import com.learn.springrest.model.Role;
import com.learn.springrest.model.RoleEnum;
import com.learn.springrest.model.User;
import com.learn.springrest.repository.RoleRepository;
import com.learn.springrest.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    @Override
    public User register(RegisterDTO user) {
        // Here you would typically check if the user already exists,

        userRepository.findByUsername(user.getUsername())
                .ifPresent(existingUser -> {
                    throw new EmailAlreadyExistsException("User already exists with username: " + user.getUsername());
                });

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        if (optionalRole.isEmpty()) {
            throw new ResourceNotFoundException("Role not found: " + RoleEnum.USER);
        }


        // Convert RegisterDTO to User entity
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(optionalRole.get());

        return userRepository.save(newUser);
    }

    @Override
    public User login(LoginDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + input.getUsername()));

    }

}
