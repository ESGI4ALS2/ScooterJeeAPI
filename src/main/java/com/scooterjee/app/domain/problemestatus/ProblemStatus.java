package com.scooterjee.app.domain.problemestatus;

import com.scooterjee.kernel.Entity;

public class ProblemStatus extends Entity<Long> {

    public static final String STATUS_OPEN = "open";
    public static final String STATUS_WAITING_FOR_REVIEW = "waiting for review";
    public static final String STATUS_CLOSED = "closed";

    private final String name;

    public ProblemStatus(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
