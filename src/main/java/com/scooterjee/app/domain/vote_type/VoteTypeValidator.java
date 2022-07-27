package com.scooterjee.app.domain.vote_type;

import com.scooterjee.app.domain.vote.exception.InvalidVoteException;
import com.scooterjee.kernel.Validator;

public class VoteTypeValidator implements Validator<VoteType> {


    @Override
    public void validate(VoteType type) {
        if(type == null) {
            throw new IllegalArgumentException("Vote type to validate is null");
        }

        if (type.getName() == null || type.getName().isEmpty()) {
            throw new InvalidVoteException("VoteType name cannot be null");
        }

    }
}

