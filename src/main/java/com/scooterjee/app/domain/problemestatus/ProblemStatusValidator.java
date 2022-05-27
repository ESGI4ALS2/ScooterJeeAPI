package com.scooterjee.app.domain.problemestatus;

import com.scooterjee.app.domain.categories.exception.InvalidCategoriesException;
import com.scooterjee.kernel.Validator;

public class ProblemStatusValidator implements Validator<ProblemStatus> {

    @Override
    public void validate(ProblemStatus problemStatus) {
        if(problemStatus == null) {
            throw new IllegalArgumentException("problem status to validate is null");
        }

        if(problemStatus.getName() == null || problemStatus.getName().isEmpty()){
            throw new InvalidCategoriesException("problem status name can not be empty");
        }
    }
}
