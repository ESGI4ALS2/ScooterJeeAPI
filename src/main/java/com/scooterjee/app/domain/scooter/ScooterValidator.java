package com.scooterjee.app.domain.scooter;

import com.scooterjee.app.domain.scooter.exception.InvalidScooterException;
import com.scooterjee.app.domain.scootermodel.ScooterModelValidator;
import com.scooterjee.app.domain.user.UserValidator;
import com.scooterjee.kernel.Validator;

public class ScooterValidator implements Validator<Scooter> {

    private final ScooterModelValidator scooterModelValidator;
    private final UserValidator userValidator;

    public ScooterValidator(ScooterModelValidator scooterModelValidator, UserValidator userValidator) {
        this.scooterModelValidator = scooterModelValidator;
        this.userValidator = userValidator;
    }

    @Override
    public void validate(Scooter scooter) {
        if(scooter == null) {
            throw new IllegalArgumentException("User to validate is null");
        }

        if(scooter.getSerialNumber() == null || scooter.getSerialNumber().isEmpty()){
            throw new InvalidScooterException("scooter serial ID can not be empty");
        }

        if( scooter.getPurchaseDate() == null){
            throw new InvalidScooterException("scooter purchase date can not be empty");
        }

        if(scooter.getModel() == null){
            throw new InvalidScooterException("scooter model can not be empty");
        }
        scooterModelValidator.validate(scooter.getModel());

        if(scooter.getOwner() == null){
            throw new InvalidScooterException("scooter owner can not be empty");
        }
        userValidator.validate(scooter.getOwner());

    }
}
