package com.scooterjee.kernel;

import com.scooterjee.kernel.exception.SimpleServiceException;
import com.scooterjee.kernel.exception.SimpleServiceObjectAlreadyException;
import com.scooterjee.kernel.exception.SimpleServiceObjectNotFoundException;

import java.util.List;

public abstract class SimpleService<R extends Repository<V, K> , V extends Entity<K>, K > {


    protected final R repository;
    protected final Validator<V> validator;
    protected final String serviceObjectName;

    public SimpleService(R repository, Validator<V> validator, String serviceObjectName) {
        this.repository = repository;
        this.validator = validator;
        this.serviceObjectName = serviceObjectName;
    }

    public V get(K key) {
        return repository.get(key).orElseThrow(() -> getExceptionWhenObjectNotFound(key));
    }

    public void add(V value) {
        validator.validate(value);

        if (value.getID() != null && repository.get(value.getID()).isPresent()) {
            throw getExceptionWhenObjectAlreadyPresent(value.getID());
        }

        repository.add(value);
    }

    public void update(V value) {
        if( !repository.update(value)){
            throw getExceptionWhenObjectNotFound(value.getID());
        }
    }

    public void remove(K key) {
        if (!repository.remove(key)) {
            throw getExceptionWhenObjectNotFound(key);
        }
    }

    public List<V> getAll() {
        return repository.getAll();
    }

    public SimpleServiceObjectNotFoundException getExceptionWhenObjectNotFound(K key){
        return new SimpleServiceObjectNotFoundException(serviceObjectName, key.toString());
    }

    public SimpleServiceException getExceptionWhenObjectAlreadyPresent(K key){
        return new SimpleServiceObjectAlreadyException(serviceObjectName, key.toString());
    }
}
