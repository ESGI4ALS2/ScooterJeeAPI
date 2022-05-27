package com.scooterjee.kernel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class InMemoryRepositoryTest<V extends Entity<K>, K> {

    private final V value1;
    private final V value2;

    public InMemoryRepositoryTest(V value1, V value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    protected abstract Repository<V, K> getNewRepository(List<V> values);

    abstract protected V getUpdateValue1();

    Repository<V, K> getNewRepository() {
        return getNewRepository(new ArrayList<>());
    }

    @Test
    void should_add_new_skill() {
        Repository<V, K> repository = getNewRepository();

        List<V> expected = List.of(value1);
        repository.add(value1);

        Assertions.assertEquals(expected, repository.getAll());
    }

    @Test
    void should_add_new_skill_even_if_already_pressent() {
        Repository<V, K> repository = getNewRepository();

        List<V> expected = List.of(value1, value1);
        repository.add(value1);
        repository.add(value1);

        Assertions.assertEquals(expected, repository.getAll());
    }

    @Test
    void should_remove_skill() {
        Repository<V, K> repository = getNewRepository(List.of(value1));

        List<V> expected = List.of();
        repository.remove(value1.getID());

        Assertions.assertEquals(expected, repository.getAll());
    }

    @Test
    void should_remove_all_matching_skill() {
        Repository<V, K> repository = getNewRepository(List.of(value1, value1));

        List<V> expected = List.of();
        repository.remove(value1.getID());

        Assertions.assertEquals(expected, repository.getAll());
    }

    @Test
    void should_remove_nothing() {
        Repository<V, K> repository = getNewRepository(List.of(value1));
        List<V> expected = List.of(value1);
        repository.remove(value2.getID());

        Assertions.assertEquals(expected, repository.getAll());
    }

    @Test
    void should_get_skill() {
        Repository<V, K> repository = getNewRepository(List.of(value1));

        Assertions.assertEquals(Optional.of(value1), repository.get(value1.getID()));
    }

    @Test
    void should_get_nothing() {
        Repository<V, K> repository = getNewRepository(List.of(value1));

        Assertions.assertEquals(Optional.empty(), repository.get(value2.getID()));
    }

    @Test
    void should_update(){
        Repository<V, K> repository = getNewRepository(List.of(value1));
        V update = getUpdateValue1();
        Assertions.assertDoesNotThrow(() ->   repository.update(update));
        Assertions.assertEquals( Optional.of(update), repository.get(value1.getID()));
    }

}
