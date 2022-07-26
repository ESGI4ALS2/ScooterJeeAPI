package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.address.AddressRepository;
import com.scooterjee.app.infrastructure.database.address.AddressDB;
import com.scooterjee.app.infrastructure.database.address.AddressDBRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class InDBAddressRepository implements AddressRepository {

    private final AddressDBRepository dbRepository;

    public InDBAddressRepository(AddressDBRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    public Optional<Address> get(Long key) {
        return Optional.of(dbRepository.findById(key).orElseThrow().toAddress());
    }

    @Override
    public Long add(Address value) {
        AddressDB addressDB = dbRepository.save(AddressDB.of(value));
        value.setId(addressDB.getAddressID());
        return addressDB.getAddressID();
    }

    @Override
    public boolean update(Address value) {
        return false;
    }

    @Override
    public boolean remove(Long value) {
        dbRepository.deleteById(value);
        return dbRepository.existsById(value);
    }

    @Override
    public List<Address> getAll() {
        List<Address> addresses = new ArrayList<>();
        dbRepository.findAll().forEach(addressDB -> addresses.add(addressDB.toAddress()));
        return addresses;
    }
}
