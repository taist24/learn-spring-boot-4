package com.learnspringboot.week2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.learnspringboot.week2.dto.LoginResponseDto;
import com.learnspringboot.week2.dto.LoginUserDto;
import com.learnspringboot.week2.dto.RegisterUserDto;
import com.learnspringboot.week2.dto.UserResponseDto;
import com.learnspringboot.week2.entity.User;
import com.learnspringboot.week2.service.AuthService;
import com.learnspringboot.week2.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserResponseDto registerUser(@Valid @RequestBody RegisterUserDto registerUserDto)
            throws ResponseStatusException {
        User newUser = authService.registerUser(registerUserDto);
        return UserResponseDto.from(newUser);
    }

    @PostMapping("/login")
    @ResponseBody
    public LoginResponseDto loginUser(@Valid @RequestBody LoginUserDto loginUserDto) {
        return authService.loginUser(loginUserDto);
    }

}
