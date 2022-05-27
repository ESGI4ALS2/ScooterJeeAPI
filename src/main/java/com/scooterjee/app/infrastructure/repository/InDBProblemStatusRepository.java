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
        Optional<ProblemStatusDB> problemStatusDB = dbRepository.getProblemStatusDBByName(name);

        if(problemStatusDB.isEmpty()){
            return Optional.empty();
        }

        return Optional.of( problemStatusDB.get().toProblemStatus());
    }

    @Override
    public Optional<ProblemStatus> get(Long key) {
        Optional<ProblemStatusDB> problemStatusDB  = dbRepository.findById(key);

        if(problemStatusDB.isEmpty()){
            return Optional.empty();
        }

        return Optional.of( problemStatusDB.get().toProblemStatus() );
    }

    @Override
    public Long add(ProblemStatus value) {
        ProblemStatusDB problemStatusDB = dbRepository.save(ProblemStatusDB.of(value));
        value.setId(problemStatusDB.getProblemStatusID());
        return problemStatusDB.getProblemStatusID();
    }

    @Override
    public boolean update(ProblemStatus value) {
        return false;
    }

    @Override
    public boolean remove(Long value) {
        dbRepository.deleteById(value);
        return dbRepository.existsById(value);
    }

    @Override
    public List<ProblemStatus> getAll() {
        List<ProblemStatus> list = new ArrayList<>();
        dbRepository.findAll().forEach(problemStatusDB -> list.add(problemStatusDB.toProblemStatus()));
        return list;
    }
}
