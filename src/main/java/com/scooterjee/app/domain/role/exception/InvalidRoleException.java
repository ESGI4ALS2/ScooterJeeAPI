package com.scooterjee.app.domain.role.exception;

import com.scooterjee.kernel.exception.SimpleServiceException;

public class InvalidRoleException extends SimpleServiceException{
    public InvalidRoleException(String message) {
        super(message);
    }
}
