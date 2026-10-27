package com.learnspringboot.week2.dto;

import com.learnspringboot.week2.entity.User;

/**
 * UserResponseDto
 */
public record UserResponseDto(
        Long id,
        String name,
        String email,
        String gender,
        User.Role role) {
    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getGender().name(),
                user.getRole());
    }
}
