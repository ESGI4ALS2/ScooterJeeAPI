package com.scooterjee.app.domain.vote_type;

import com.scooterjee.kernel.Entity;

public class VoteType extends Entity<Long> {
    private final String name;

    public VoteType(
        Long id, 
        String name
    ) {
        super(id);
        this.name = name;
    }
}
