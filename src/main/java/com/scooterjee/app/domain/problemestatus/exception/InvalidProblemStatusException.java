package com.scooterjee.app.domain.problemestatus.exception;

import com.scooterjee.kernel.exception.SimpleServiceException;

public class InvalidProblemStatusException extends SimpleServiceException {
    public InvalidProblemStatusException(String message) {
        super(message);
    }
}
