package com.learnspringboot.week1.controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.learnspringboot.week1.dto.CreateUserDto;
import com.learnspringboot.week1.dto.UpdateUserDto;
import com.learnspringboot.week1.dto.UserResponseDto;
import com.learnspringboot.week1.entity.User;
import com.learnspringboot.week1.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping({ "/", "/index", "" })
    @ResponseBody
    public Page<UserResponseDto> index(@PageableDefault(size = 15, sort = "id") Pageable pageable) {
        Page<User> usersPageable = service.getAllUsers(pageable);
        return usersPageable.map(UserResponseDto::toResponse);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "User deleted successfully.";
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public User updateUser(@Valid @RequestBody UpdateUserDto updateUserDto, @PathVariable Long id) {
        return service.updateUser(updateUserDto, id);
    }

    @PostMapping
    @ResponseBody
    public User createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return service.createUser(createUserDto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/upload-avatar/{id}")
    @ResponseBody
    public User uploadUserAvatar(@RequestParam MultipartFile file, @PathVariable Long id) throws IOException {

        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please select a file to upload.");
        }

        return service.uploadUserAvatar(id, file);
    }

}
