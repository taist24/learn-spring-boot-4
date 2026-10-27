package com.learnspringboot.week2.dto;

import com.learnspringboot.week2.entity.User.Gender;
import com.learnspringboot.week2.validation.ValueOfEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserDto {

    @Size(max = 100, message = "Name must be less than or equal to 100 characters")
    @Size(min = 1, message = "Name must be at least 1 character")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @ValueOfEnum(enumClass = Gender.class)
    private String gender;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
