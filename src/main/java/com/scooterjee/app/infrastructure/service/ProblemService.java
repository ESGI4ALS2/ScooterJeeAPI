package com.scooterjee.app.infrastructure.service;

import com.scooterjee.app.domain.problem.Problem;
import com.scooterjee.app.domain.problem.ProblemRepository;
import com.scooterjee.app.domain.problemestatus.ProblemStatus;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import com.scooterjee.kernel.exception.SimpleServiceObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProblemService extends SimpleService<ProblemRepository, Problem, Long> {
    public ProblemService(ProblemRepository repository, Validator<Problem> validator) {
        super(repository, validator, "problem");
    }

    public void putReferentOnProblem(User user, Long problem_id) {
        Problem problem = repository
            .get(problem_id)
            .orElseThrow(() -> new SimpleServiceObjectNotFoundException("problem",problem_id.toString())
        );

        problem.setReferent(user);
        repository.update(problem);
    }

    public void putProblemStatusOnProblem(ProblemStatus problemStatus, Long problem_id) {
        Problem problem = repository
            .get(problem_id)
            .orElseThrow(()->new SimpleServiceObjectNotFoundException("problem",problem_id.toString())
        );
        problem.setStatus(problemStatus);
        repository.update(problem);
    }

    public List<Problem> getAllAvailable(User user){
        if(user.getAssignedCategories().isEmpty()){
            return List.of();
        }
        List<Problem> list = repository.getAllAvailableProblem(user.getAssignedCategories().get(0).getID());

        return list.stream().filter(user::isUserAvailableForProblem).collect(Collectors.toList());
    }
}
