package com.learnspringboot.week2.dto;

public record LoginResponseDto(
        String accessToken,
        String tokenType,
        Long expiresIn,
        UserResponseDto user) {

    public static LoginResponseDto from(String accessToken, UserResponseDto user, Long expiresIn) {
        return new LoginResponseDto(accessToken, "Bearer", expiresIn, user);
    }
}
