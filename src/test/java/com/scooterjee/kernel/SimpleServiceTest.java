package com.scooterjee.kernel;

import com.scooterjee.kernel.exception.SimpleServiceObjectAlreadyException;
import com.scooterjee.kernel.exception.SimpleServiceObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleServiceTest<R extends Repository<V, K>, V extends Entity<K>, K> {

    private Validator<V> validator;
    private R repository;
    private SimpleService<R, V, K> service;

    private final V value1;
    private final V value2;

    private final Class<? extends Throwable> exceptionClassWhenObjectNotFound;
    private final Class<? extends Throwable> exceptionClassWhenObjectAlreadyPresent;

    public SimpleServiceTest(V value1, V value2,
                             Class<? extends Throwable> exceptionClassWhenObjectNotFound,
                             Class<? extends Throwable> exceptionClassWhenObjectAlreadyPresent) {
        this.value1 = value1;
        this.value2 = value2;
        this.exceptionClassWhenObjectNotFound = exceptionClassWhenObjectNotFound;
        this.exceptionClassWhenObjectAlreadyPresent = exceptionClassWhenObjectAlreadyPresent;
    }

    public SimpleServiceTest(V value1, V value2) {
        this(value1, value2, SimpleServiceObjectNotFoundException.class, SimpleServiceObjectAlreadyException.class);
    }

    @BeforeEach
    void beforeEach() {
        validator = getNewValidator();
        repository = getNewRepository();
        service = getNewService();
    }

    protected abstract Validator<V> getNewValidator();

    protected abstract R getNewRepository(List<V> values);

    protected abstract R getNewRepository();

    protected abstract SimpleService<R, V, K> getNewService();

    protected abstract SimpleService<R, V, K> getNewService(R repo, Validator<V> validator);


    @Test
    void should_add_new_object_if_not_already_present() {
        List<V> expected = List.of(value1);

        Assertions.assertDoesNotThrow(() -> service.add(value1));
        Assertions.assertEquals(expected, service.getAll());
    }

    @Test
    void should_not_add_new_object_if_already_present() {
        List<V> expected = List.of(value1);

        Assertions.assertDoesNotThrow(() -> service.add(value1));
        Assertions.assertThrows(exceptionClassWhenObjectAlreadyPresent, () -> service.add(value1));
        Assertions.assertEquals(expected, service.getAll());
    }

    @Test
    void should_remove_object_if_present() {
        List<V> expected = List.of(value1);

        repository = getNewRepository(new ArrayList<>(List.of(value1, value2)));
        service = getNewService(repository, validator);

        Assertions.assertDoesNotThrow(() -> service.remove(value2.getID()));
        Assertions.assertEquals(expected, service.getAll());
    }

    @Test
    void should_not_remove_object_if_not_present() {
        List<V> expected = List.of(value1);

        repository = getNewRepository(new ArrayList<>(List.of(value1)));
        service = getNewService(repository, validator);

        Assertions.assertThrows(exceptionClassWhenObjectNotFound, () -> service.remove(value2.getID()));
        Assertions.assertEquals(expected, service.getAll());
    }

    @Test
    void should_get_object_if_present() {
        repository = getNewRepository(new ArrayList<>(List.of(value1)));
        service = getNewService(repository, validator);

        Assertions.assertEquals(value1, service.get(value1.getID()));
    }

    @Test
    void should_not_get_object_if_not_present() {
        repository = getNewRepository(new ArrayList<>(List.of(value1)));
        service = getNewService(repository, validator);

        Assertions.assertThrows(exceptionClassWhenObjectNotFound, () -> service.get(value2.getID()));
    }

}