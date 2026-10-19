package com.learnspringboot.week1.dto;

import com.learnspringboot.week1.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateUserDto {
    @NotBlank()
    @Size(min = 3, max = 50)
    private String name;

    @NotNull()
    @Email()
    @Size(min = 5, max = 100)
    private String email;

    @NotNull
    private User.Gender gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User.Gender getGender() {
        return gender;
    }

    public void setGender(User.Gender gender) {
        this.gender = gender;
    }

}
