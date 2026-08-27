package com.example.pricewatcher.Service;

import com.example.pricewatcher.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UUID create(User account);
    User update(User account, UUID uuid);
    List<User> findAll();
    User findById(UUID uuid);
    void delete(UUID uuid);

}
