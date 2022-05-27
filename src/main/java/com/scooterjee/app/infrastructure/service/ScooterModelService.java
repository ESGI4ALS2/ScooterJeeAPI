package com.scooterjee.app.infrastructure.service;

import com.scooterjee.app.domain.scootermodel.ScooterModel;
import com.scooterjee.app.domain.scootermodel.ScooterModelRepository;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import org.springframework.stereotype.Service;

@Service
public class ScooterModelService extends SimpleService<ScooterModelRepository, ScooterModel, Long> {

    public ScooterModelService(ScooterModelRepository repository, Validator<ScooterModel> validator) {
        super(repository, validator, "scooter model");
    }

    public ScooterModel getByName(String name) {
        return repository.getByName(name).orElse(null);
    }
}
