package com.scooterjee.app.domain.scootermodel;

import com.scooterjee.kernel.Entity;

public class ScooterModel extends Entity<Long> {

    private final String name;

    public ScooterModel(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
