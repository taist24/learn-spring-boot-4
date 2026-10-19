package com.learnspringboot.week1.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.learnspringboot.week1.dto.CreateUserDto;
import com.learnspringboot.week1.dto.UpdateUserDto;
import com.learnspringboot.week1.entity.User;
import com.learnspringboot.week1.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User createUser(CreateUserDto createUserDto) {
        Optional<User> user = userRepository.findByEmail(createUserDto.getEmail());

        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A user with the same email already exists.");
        }

        User newUser = new User();
        newUser.setName(createUserDto.getName());
        newUser.setEmail(createUserDto.getEmail());
        newUser.setGender(createUserDto.getGender());

        return userRepository.save(newUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(UpdateUserDto updateUserDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        if (!updateUserDto.getName().equals(user.getName())) {
            user.setName(updateUserDto.getName());
        }

        if (!updateUserDto.getEmail().equals(user.getEmail())) {
            user.setEmail(updateUserDto.getEmail());
        }

        if (!updateUserDto.getGender().equals(user.getGender())) {
            user.setGender(updateUserDto.getGender());
        }

        return userRepository.save(user);
    }

    public User uploadUserAvatar(Long userId, MultipartFile file) throws IOException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        // Create upload directory if it doesn't exist
        Path uploadDir = Path.of("uploads");
        Files.createDirectories(uploadDir);

        // Save the file to the upload directory
        String safeFilename = UUID.randomUUID() + "_" + Path.of(file.getOriginalFilename()).getFileName();
        Path destinationFile = uploadDir.resolve(safeFilename);
        file.transferTo(destinationFile);

        // Update the user's avatar
        user.setAvatar(safeFilename);
        userRepository.save(user);

        return user;
    }

}
