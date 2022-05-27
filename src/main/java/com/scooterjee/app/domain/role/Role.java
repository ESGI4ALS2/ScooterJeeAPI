package com.scooterjee.app.domain.role;

import com.scooterjee.kernel.Entity;

public class Role extends Entity<Long> {

    private final String name;

    public Role(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
