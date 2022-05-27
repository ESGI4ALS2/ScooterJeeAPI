package com.scooterjee.kernel;

import java.util.Objects;

public abstract class Entity <T>{

    protected T id;

    public Entity(T id) {
        this.id = id;
    }

    public T getID() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity<?>)) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
