package com.example.pricewatcher.Service;

import com.example.pricewatcher.model.Store;

import java.util.List;
import java.util.UUID;

public interface StoreService {

    UUID create(Store store);

    List<Store> findAll();

    Store findById(UUID id);

    Store update(UUID id, Store store);

    void delete(UUID id);
}
