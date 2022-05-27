package com.scooterjee.app.domain.problem.exception;

import com.scooterjee.kernel.exception.SimpleServiceException;

public class InvalidProblemException extends SimpleServiceException {
    public InvalidProblemException(String message) {
        super(message);
    }
}
