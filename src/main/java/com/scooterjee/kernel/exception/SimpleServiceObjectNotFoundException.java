package com.scooterjee.kernel.exception;

public class SimpleServiceObjectNotFoundException extends SimpleServiceException{
    public SimpleServiceObjectNotFoundException(String objectName, String objectKey) {
        super(objectName + " with key " + objectKey + " does not exist");
    }
}
