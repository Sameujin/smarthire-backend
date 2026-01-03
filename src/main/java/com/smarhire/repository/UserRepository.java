package com.smarhire.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarhire.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
