package com.scooterjee.app.domain.user.exception;

public class OperationNotPermittedException extends RuntimeException{
    public OperationNotPermittedException(String message) {
        super(message);
    }
}
