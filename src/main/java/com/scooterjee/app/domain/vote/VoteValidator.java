package com.scooterjee.app.domain.vote;

import com.scooterjee.app.domain.vote.exception.InvalidVoteException;
import com.scooterjee.kernel.Validator;

public class VoteValidator implements Validator<Vote> {

    @Override
    public void validate(Vote vote) {
        if(vote == null) {
            throw new IllegalArgumentException("Vote to validate is null");
        }

        if(vote.getVoteDate() == null){
            throw new InvalidVoteException("Vote date can not be empty");
        }

        if(vote.getReferent() == null){
            throw new InvalidVoteException("Vote referent can not be empty");
        }

        if(vote.getVoter() == null){
            throw new InvalidVoteException("Vote creator can not be empty");
        }

        if (vote.getType() == null) {
            throw new InvalidVoteException("Vote type can not be empty");
        }
    }
}

