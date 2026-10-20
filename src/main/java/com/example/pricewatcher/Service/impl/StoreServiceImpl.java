package com.example.pricewatcher.Service.impl;

import com.example.pricewatcher.Service.StoreService;
import com.example.pricewatcher.exception.StoreAlreadyExistsException;
import com.example.pricewatcher.exception.StoreNotFoundException;
import com.example.pricewatcher.model.Store;
import com.example.pricewatcher.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public UUID create(Store store) {

        if (storeRepository.existsByName(store.getName())) {
            throw new StoreAlreadyExistsException(store.getName());
        }

        return storeRepository.save(store).getId();
    }

    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store findById(UUID id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException(id));
    }

    @Override
    public Store update(UUID id, Store store) {

        Store existing = findById(id);

        boolean nameChanged =
                !existing.getName().equalsIgnoreCase(store.getName());

        if (nameChanged &&
                storeRepository.existsByName(store.getName())) {
            throw new StoreAlreadyExistsException(store.getName());
        }

        existing.setName(store.getName());
        existing.setWebsiteUrl(store.getWebsiteUrl());

        return storeRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {

        if (!storeRepository.existsById(id)) {
            throw new StoreNotFoundException(id);
        }

        storeRepository.deleteById(id);
    }
}
