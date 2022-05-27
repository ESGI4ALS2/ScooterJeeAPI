package com.scooterjee.app.infrastructure.service;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.address.AddressRepository;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends SimpleService<AddressRepository, Address, Long> {
    public AddressService(AddressRepository repository, Validator<Address> validator) {
        super(repository, validator, "address");
    }
}
