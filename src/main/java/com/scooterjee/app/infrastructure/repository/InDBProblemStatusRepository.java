package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.problemestatus.ProblemStatus;
import com.scooterjee.app.domain.problemestatus.ProblemStatusRepository;
import com.scooterjee.app.infrastructure.database.problemstatus.ProblemStatusDB;
import com.scooterjee.app.infrastructure.database.problemstatus.ProblemStatusDBRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InDBProblemStatusRepository implements ProblemStatusRepository {

    private final ProblemStatusDBRepository dbRepository;

    public InDBProblemStatusRepository(ProblemStatusDBRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    public Optional<ProblemStatus> getByName(String name) {
        return dbRepository.getProblemStatusDBByName(name).map(ProblemStatusDB::toProblemStatus);
    }

    @Override
    public Optional<ProblemStatus> get(Long key) {
        return dbRepository.findById(key).map(ProblemStatusDB::toProblemStatus);
    }

    @Override
    public Long add(ProblemStatus value) {
        ProblemStatusDB problemStatusDB = dbRepository.save(ProblemStatusDB.of(value));
        value.setId(problemStatusDB.getProblemStatusID());
        return problemStatusDB.getProblemStatusID();
    }

    @Override
    public boolean update(ProblemStatus value) {
        if (!dbRepository.existsById(value.getID())) {
            return false;
        }
        dbRepository.save(ProblemStatusDB.of(value));
        return true;
    }

    @Override
    public boolean remove(Long value) {
        dbRepository.deleteById(value);
        return !dbRepository.existsById(value);
    }

    @Override
    public List<ProblemStatus> getAll() {
        List<ProblemStatus> list = new ArrayList<>();
        dbRepository.findAll().forEach(problemStatusDB -> list.add(problemStatusDB.toProblemStatus()));
        return list;
    }
}
