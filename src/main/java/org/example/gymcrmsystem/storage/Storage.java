package org.example.gymcrmsystem.storage;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Storage<T> {

    private final Map<Long, T> storageMap;

    public Storage() {
        this.storageMap = new HashMap<>();
    }

    public void save(Long id, T entity) {
        storageMap.put(id, entity);
    }

    public T get(Long id) {
        return storageMap.get(id);
    }

    public void remove(Long id) {
        storageMap.remove(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(storageMap.values());
    }
}