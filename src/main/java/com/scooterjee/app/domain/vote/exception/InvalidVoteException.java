package com.scooterjee.app.domain.vote.exception;

import com.scooterjee.kernel.exception.SimpleServiceException;

public class InvalidVoteException extends SimpleServiceException {
    public InvalidVoteException(String message) {
        super(message);
    }
}
