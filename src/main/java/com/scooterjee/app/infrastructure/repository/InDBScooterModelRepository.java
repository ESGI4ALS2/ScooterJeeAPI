package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.scootermodel.ScooterModel;
import com.scooterjee.app.domain.scootermodel.ScooterModelRepository;
import com.scooterjee.app.infrastructure.database.scootermodel.ScooterModelDB;
import com.scooterjee.app.infrastructure.database.scootermodel.ScooterModelDBRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InDBScooterModelRepository implements ScooterModelRepository {

    private final ScooterModelDBRepository dbRepository;

    public InDBScooterModelRepository(ScooterModelDBRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    public Optional<ScooterModel> get(Long key) {
        Optional<ScooterModelDB> scooterModelDB = dbRepository.findById(key);

        if (scooterModelDB.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(scooterModelDB.get().toScooterModel());
    }

    @Override
    public Long add(ScooterModel value) {
        ScooterModelDB scooterModelDB = dbRepository.save(ScooterModelDB.of(value));
        value.setId(scooterModelDB.getScooterModelID());
        return scooterModelDB.getScooterModelID();
    }

    @Override
    public boolean update(ScooterModel value) {
        return false;
    }

    @Override
    public boolean remove(Long value) {
        dbRepository.deleteById(value);
        return dbRepository.existsById(value);
    }

    @Override
    public List<ScooterModel> getAll() {
        List<ScooterModel> list = new ArrayList<>();
        dbRepository.findAll().forEach(scooterModelDB -> list.add(scooterModelDB.toScooterModel()));
        return list;
    }

    @Override
    public Optional<ScooterModel> getByName(String name) {
        Optional<ScooterModelDB> optionalScooterModel = dbRepository.findByName(name);
        if (optionalScooterModel.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of( optionalScooterModel.get().toScooterModel());
    }
}
