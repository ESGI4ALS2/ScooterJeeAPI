package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.scooter.Scooter;
import com.scooterjee.app.domain.scooter.ScooterRepository;
import com.scooterjee.app.infrastructure.database.scooter.ScooterDB;
import com.scooterjee.app.infrastructure.database.scooter.ScooterDBRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InDBScooterRepository implements ScooterRepository {

    private final ScooterDBRepository dbRepository;

    public InDBScooterRepository(ScooterDBRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    public Optional<Scooter> get(Long key) {
        return dbRepository.findById(key).map(ScooterDB::toScooter);
    }

    @Override
    public Long add(Scooter value) {
        ScooterDB scooterDB = dbRepository.save(ScooterDB.of(value));
        value.setId(scooterDB.getScooterID());
        return scooterDB.getScooterID();
    }

    @Override
    public boolean update(Scooter value) {
        if (!dbRepository.existsById(value.getID())) {
            return false;
        }
        dbRepository.save(ScooterDB.of(value));
        return true;
    }

    @Override
    public boolean remove(Long value) {
        dbRepository.deleteById(value);
        return !dbRepository.existsById(value);
    }

    @Override
    public List<Scooter> getAll() {
        List<Scooter> list = new ArrayList<>();
        dbRepository.findAll().forEach(scooterDB -> list.add(scooterDB.toScooter()));
        return list;
    }
}
