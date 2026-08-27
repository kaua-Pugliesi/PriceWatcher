package com.example.pricewatcher.Service.impl;

import com.example.pricewatcher.Service.UserService;
import com.example.pricewatcher.model.User;
import com.example.pricewatcher.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UUID create(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public User update(User user, UUID uuid) {
        User existing = findById(uuid);
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Override
    public void delete(UUID uuid) {
        userRepository.deleteById(uuid);
    }
}
