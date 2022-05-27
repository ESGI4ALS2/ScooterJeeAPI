package com.scooterjee.app.domain.scootermodel;

import com.scooterjee.app.domain.scootermodel.exception.InvalidScooterModelException;
import com.scooterjee.kernel.Validator;

public class ScooterModelValidator implements Validator<ScooterModel> {

    @Override
    public void validate(ScooterModel scooterModel) {
        if(scooterModel == null) {
            throw new IllegalArgumentException("ScooterModel to validate is null");
        }

        if( scooterModel.getName() == null || scooterModel.getName().isEmpty()) {
            throw new InvalidScooterModelException("Scooter model name can not be empty");
        }
    }

}
