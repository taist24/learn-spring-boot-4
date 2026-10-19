package com.learnspringboot.week1.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learnspringboot.week1.entity.User;
import com.learnspringboot.week1.entity.User.Gender;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        Gender gender,
        String avatar) {
    public static UserResponseDto from(User user, String avatarUrl) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getGender(),
                avatarUrl);
    }

    public static UserResponseDto toResponse(User user) {
        String avatarUrl = null;

        if (user.getAvatar() != null) {
            avatarUrl = ServletUriComponentsBuilder.fromCurrentContextPath().pathSegment("uploads", user.getAvatar())
                    .build().encode().toUriString();
        }

        return UserResponseDto.from(user, avatarUrl);
    }
}
