package com.scooterjee.kernel.email.exception;


import com.scooterjee.kernel.email.EmailAddress;

public class InvalidEmailAddressException extends EmailAddressException{
    public InvalidEmailAddressException(EmailAddress emailAddress) {
        super("email " + emailAddress.toString() + " is invalid");
    }
}
