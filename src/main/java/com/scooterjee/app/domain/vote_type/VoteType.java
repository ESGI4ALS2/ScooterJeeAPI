package com.scooterjee.app.domain.vote_type;

import com.scooterjee.kernel.Entity;

import java.util.Objects;

public class VoteType extends Entity<Long> {
    public static final VoteType UP_VOTE = new VoteType(1L, "up");
    public static final VoteType DOWN_VOTE = new VoteType(2L, "down");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VoteType voteType = (VoteType) o;
        return Objects.equals(name, voteType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
