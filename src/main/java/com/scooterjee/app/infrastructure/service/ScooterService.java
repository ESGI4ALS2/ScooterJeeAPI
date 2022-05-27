package com.scooterjee.app.infrastructure.service;

import com.scooterjee.app.domain.scooter.Scooter;
import com.scooterjee.app.domain.scooter.ScooterRepository;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import org.springframework.stereotype.Service;

@Service
public class ScooterService extends SimpleService<ScooterRepository, Scooter, Long> {
    public ScooterService(ScooterRepository repository, Validator<Scooter> validator) {
        super(repository, validator, "scooter");
    }
}
