package com.scooterjee.app.domain.address.exception;

import com.scooterjee.kernel.exception.SimpleServiceException;

public class InvalidAddressException extends SimpleServiceException {
    public InvalidAddressException(String message) {
        super(message);
    }
}
