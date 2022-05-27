package com.scooterjee.app.domain.scooter.exception;

import com.scooterjee.kernel.exception.SimpleServiceException;

public class InvalidScooterException extends SimpleServiceException {
    public InvalidScooterException(String message) {
        super(message);
    }
}
