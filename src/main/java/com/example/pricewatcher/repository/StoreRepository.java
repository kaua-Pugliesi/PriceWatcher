package com.example.pricewatcher.repository;

import com.example.pricewatcher.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    boolean existsByName(String name);
}
