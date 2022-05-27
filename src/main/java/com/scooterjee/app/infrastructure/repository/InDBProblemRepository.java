package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.problem.Problem;
import com.scooterjee.app.domain.problem.ProblemRepository;
import com.scooterjee.app.infrastructure.database.problems.ProblemsDB;
import com.scooterjee.app.infrastructure.database.problems.ProblemsDBRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InDBProblemRepository implements ProblemRepository {

    private final ProblemsDBRepository dbRepository;

    public InDBProblemRepository(ProblemsDBRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    public Optional<Problem> get(Long key) {
        Optional<ProblemsDB> problemsDB = dbRepository.findById(key);


        if(problemsDB.isEmpty()){
            return Optional.empty();
        }

        return Optional.of( problemsDB.get().toProblem());
    }

    @Override
    public Long add(Problem value) {
        ProblemsDB problemsDB = dbRepository.save(ProblemsDB.of(value));
        value.setId(problemsDB.getId());
        return problemsDB.getId();
    }

    @Override
    public boolean update(Problem value) {
        dbRepository.save(ProblemsDB.of(value));
        return true;
    }

    @Override
    public boolean remove(Long value) {
        dbRepository.deleteById(value);
        return dbRepository.existsById(value);
    }

    @Override
    public List<Problem> getAll() {
        List<Problem> problems = new ArrayList<>();
        dbRepository.findAll().forEach(problemsDB -> problems.add(problemsDB.toProblem()));
        return problems;
    }

    @Override
    public List<Problem> getAllAvailableProblem(Long categoryID) {
        return dbRepository.getProblemAvailableForUser(categoryID).stream().map(ProblemsDB::toProblem).collect(Collectors.toList());
    }
}
