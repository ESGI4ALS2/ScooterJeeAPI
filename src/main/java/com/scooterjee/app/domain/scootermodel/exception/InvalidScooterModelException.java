package com.scooterjee.app.domain.scootermodel.exception;

import com.scooterjee.kernel.exception.SimpleServiceException;

public class InvalidScooterModelException extends SimpleServiceException {
    public InvalidScooterModelException(String message) {
        super(message);
    }
}
