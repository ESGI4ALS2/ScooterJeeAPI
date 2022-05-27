package com.scooterjee.app.domain.problemestatus;

import com.scooterjee.kernel.Entity;

public class ProblemStatus extends Entity<Long> {

    private final String name;

    public ProblemStatus(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
