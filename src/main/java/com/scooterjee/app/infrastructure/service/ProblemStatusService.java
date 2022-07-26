package com.scooterjee.app.infrastructure.service;

import com.scooterjee.app.domain.problemestatus.ProblemStatus;
import com.scooterjee.app.domain.problemestatus.ProblemStatusRepository;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import com.scooterjee.kernel.exception.SimpleServiceObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProblemStatusService extends SimpleService<ProblemStatusRepository, ProblemStatus, Long> {
    public ProblemStatusService(ProblemStatusRepository repository, Validator<ProblemStatus> validator) {
        super(repository, validator, "problem status");
    }

    public ProblemStatus getByName(String name){
        return repository
            .getByName(name)
            .orElseThrow(() -> new SimpleServiceObjectNotFoundException("problem status", name)
        );
    }

}
