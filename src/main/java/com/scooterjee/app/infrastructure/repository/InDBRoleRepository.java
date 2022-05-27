package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.role.Role;
import com.scooterjee.app.domain.role.RoleRepository;
import com.scooterjee.app.infrastructure.database.role.RoleDB;
import com.scooterjee.app.infrastructure.database.role.RoleDBRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InDBRoleRepository implements RoleRepository {

    private final RoleDBRepository dbRepository;

    public InDBRoleRepository(RoleDBRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    public Optional<Role> get(Long key) {

        Optional<RoleDB> roleDB = dbRepository.findById(key);

        if(roleDB.isEmpty()){
            return Optional.empty();
        }

        return Optional.of( roleDB.get().toRole());
    }

    @Override
    public Long add(Role value) {
        RoleDB roleDB = dbRepository.save(RoleDB.of(value));
        value.setId(roleDB.getRoleID());
        return roleDB.getRoleID();
    }

    @Override
    public boolean update(Role value) {
        return false;
    }

    @Override
    public boolean remove(Long value) {
        dbRepository.deleteById(value);
        return !dbRepository.existsById(value);
    }

    @Override
    public List<Role> getAll() {
        List<Role> list = new ArrayList<>();
        dbRepository.findAll().forEach(roleDB -> list.add(roleDB.toRole()));
        return list;
    }
}
