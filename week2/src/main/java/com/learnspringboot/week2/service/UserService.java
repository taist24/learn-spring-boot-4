package com.learnspringboot.week2.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.learnspringboot.week2.dto.LoginUserDto;
import com.learnspringboot.week2.dto.RegisterUserDto;
import com.learnspringboot.week2.dto.UpdateUserDto;
import com.learnspringboot.week2.entity.User;
import com.learnspringboot.week2.entity.User.Gender;
import com.learnspringboot.week2.entity.User.Role;
import com.learnspringboot.week2.repository.UserRepository;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User updateUser(Long id, MultipartFile avatar, UpdateUserDto updateUserDto) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        if (avatar.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Avatar file is required.");
        }

        // Create the uploads directory if it doesn't exist
        Path uploadDirPath = Path.of("uploads");

        if (!Files.exists(uploadDirPath)) {
            try {
                Files.createDirectories(uploadDirPath);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to create uploads directory.");
            }
        }

        String finalFileName = UUID.randomUUID().toString() + "_" + avatar.getOriginalFilename();
        Path finalPath = uploadDirPath.resolve(finalFileName);

        try {
            avatar.transferTo(finalPath);
            user.setAvatar(finalFileName);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save avatar file.");
        }

        if (updateUserDto.getName() != null) {
            user.setName(updateUserDto.getName());
        }

        if (updateUserDto.getGender() != null) {
            user.setGender(Gender.valueOf(updateUserDto.getGender()));
        }

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

}
