package com.scooterjee.kernel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class InMemoryRepository<V extends Entity<K>, K> implements Repository<V, K>{

    private List<V> list;

    public InMemoryRepository(List<V> list) {
        this.list = list;
    }

    public InMemoryRepository() {
        this.list = new ArrayList<>();
    }

    @Override
    public Optional<V> get(K key) {
        return list.stream()
                .filter(item -> item.getID().equals(key))
                .findFirst();
    }

    @Override
    public K add(V value) {
        list.add(value);
        return value.id;
    }

    @Override
    public boolean update(V value) {
        if (list.stream().filter(it -> it.getID().equals(value.getID())).findFirst().isEmpty()) {
            return false;
        }
        list = list.stream()
                .filter(it -> !it.getID().equals(value.getID()))
                .collect(Collectors.toList());
        list.add(value);
        return true;
    }

    @Override
    public boolean remove(K key) {
        return list.removeIf(v -> v.getID().equals(key));
    }

    @Override
    public List<V> getAll() {
        return list;
    }
}
