package com.scooterjee.app.domain.problem;

import com.scooterjee.app.domain.problem.exception.InvalidProblemException;
import com.scooterjee.kernel.Validator;

public class ProblemValidator implements Validator<Problem> {

    @Override
    public void validate(Problem problem) {
        if(problem == null) {
            throw new IllegalArgumentException("Problem to validate is null");
        }

        if(problem.getName() == null || problem.getName().isEmpty()){
            throw new InvalidProblemException("Problem name can not be empty");
        }

        if(problem.getScooter() == null){
            throw new InvalidProblemException("Problem scooter can not be empty");
        }

        if(problem.getCategories() == null){
            throw new InvalidProblemException("Problem categories can not be empty");
        }

        if(problem.getStatus() == null){
            throw new InvalidProblemException("Problem status can not be empty");
        }

        if(problem.getDate() == null){
            throw new InvalidProblemException("Problem date can not be empty");
        }

    }
}
