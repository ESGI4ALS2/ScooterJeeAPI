package com.scooterjee.app.domain.vote;

import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.vote_type.VoteType;
import com.scooterjee.kernel.Entity;

import java.time.LocalDate;

public class Vote extends Entity<Long> {
    private final LocalDate voteDate;
    private final User voter;
    private final User referent;
    private final VoteType type;

    public Vote(
        Long id,
        LocalDate voteDate,
        User voter, 
        User referent, 
        VoteType type
    ) {
        super(id);
        this.voteDate = voteDate;
        this.voter = voter;
        this.referent = referent;
        this.type = type;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public User getVoter() {
        return voter;
    }

    public User getReferent() {
        return referent;
    }

    public VoteType getType() {
        return type;
    }
}
