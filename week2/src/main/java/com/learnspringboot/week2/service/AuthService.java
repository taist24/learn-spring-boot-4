package com.learnspringboot.week2.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.learnspringboot.week2.dto.LoginResponseDto;
import com.learnspringboot.week2.dto.LoginUserDto;
import com.learnspringboot.week2.dto.RegisterUserDto;
import com.learnspringboot.week2.dto.UserResponseDto;
import com.learnspringboot.week2.entity.User;
import com.learnspringboot.week2.entity.User.Gender;
import com.learnspringboot.week2.entity.User.Role;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public User registerUser(RegisterUserDto registerUserDto) {
        // Check if user exist
        Optional<User> existingUser = userService.findByEmail(registerUserDto.getEmail());

        // If exist throw error of Conflict
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "An account already exists with this email address.");
        }

        // If not exist create user and return success message
        User newUser = new User();

        String plainPassword = registerUserDto.getPassword();
        newUser.setPasswordHash(passwordEncoder.encode(plainPassword));

        newUser.setName(registerUserDto.getName());
        newUser.setEmail(registerUserDto.getEmail());
        newUser.setGender(Gender.valueOf(registerUserDto.getGender()));
        newUser.setRole(Role.USER); // Set default role to USER

        return userService.save(newUser);
    }

    public LoginResponseDto loginUser(LoginUserDto loginUserDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken
                    .unauthenticated(loginUserDto.getEmail(), loginUserDto.getPassword()));

            String accessToken = tokenService.generateToken(authentication);

            User user = userService.findByEmail(authentication.getName()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password."));

            return LoginResponseDto.from(accessToken, UserResponseDto.from(user),
                    TokenService.TOKEN_EXPIRATION_SECONDS);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password.");
        }
    }

}
