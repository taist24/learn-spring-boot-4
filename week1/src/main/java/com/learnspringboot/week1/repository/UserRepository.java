package com.learnspringboot.week1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnspringboot.week1.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
