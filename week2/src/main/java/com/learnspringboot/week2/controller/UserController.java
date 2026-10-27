package com.learnspringboot.week2.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learnspringboot.week2.dto.UpdateUserDto;
import com.learnspringboot.week2.dto.UserResponseDto;
import com.learnspringboot.week2.entity.User;
import com.learnspringboot.week2.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users;
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestPart("avatar") MultipartFile file,
            @PathVariable Long id,
            @Valid @ModelAttribute UpdateUserDto updateUserDto) throws IOException {
        return userService.updateUser(id, file, updateUserDto);
    }
}
