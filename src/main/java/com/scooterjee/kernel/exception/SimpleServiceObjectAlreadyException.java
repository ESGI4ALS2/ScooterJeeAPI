package com.scooterjee.kernel.exception;

public class SimpleServiceObjectAlreadyException extends SimpleServiceException {
    public SimpleServiceObjectAlreadyException(String objectName, String objectKey) {
        super(objectName + " with key " + objectKey + " already exist");
    }
}
