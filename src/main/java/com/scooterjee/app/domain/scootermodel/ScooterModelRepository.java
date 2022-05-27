package com.scooterjee.app.domain.scootermodel;

import com.scooterjee.kernel.Repository;

import java.util.Optional;

public interface ScooterModelRepository extends Repository<ScooterModel, Long> {

    Optional<ScooterModel> getByName(String name);
}
