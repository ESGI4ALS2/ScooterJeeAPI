package com.scooterjee.app.domain.categories.exception;

import com.scooterjee.kernel.exception.SimpleServiceException;

public class InvalidCategoriesException extends SimpleServiceException {
    public InvalidCategoriesException(String message) {
        super(message);
    }
}
