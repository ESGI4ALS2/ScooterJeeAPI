package com.scooterjee.app.domain.address;

import com.scooterjee.kernel.Validator;

public class AddressValidator implements Validator<Address> {
    @Override
    public void validate(Address address) {
        if(address == null) {
            throw new IllegalArgumentException("Address to validate is null");
        }
    }
}
