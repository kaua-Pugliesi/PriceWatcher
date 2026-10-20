package com.example.pricewatcher.repository;

import com.example.pricewatcher.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String Email);
    Optional<User> findByEmail(String Email);
}
