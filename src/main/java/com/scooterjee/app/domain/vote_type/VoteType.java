package com.scooterjee.app.domain.vote_type;

import com.scooterjee.kernel.Entity;

public class VoteType extends Entity<Long> {
    public static final VoteType UP = new VoteType(1L, "up");
    public static final VoteType DOWN = new VoteType(2L, "down");
    private final String name;

    public VoteType(
        Long id, 
        String name
    ) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
