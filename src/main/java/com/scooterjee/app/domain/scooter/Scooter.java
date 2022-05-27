package com.scooterjee.app.domain.scooter;

import com.scooterjee.app.domain.scootermodel.ScooterModel;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.kernel.Entity;

import java.time.LocalDate;

public class Scooter extends Entity<Long> {

    private final String serialNumber;

    private final ScooterModel model;

    private final User owner;

    private final LocalDate purchaseDate;

    public Scooter(Long id, String serialNumber, ScooterModel model, User owner, LocalDate purchaseDate) {
        super(id);
        this.serialNumber = serialNumber;
        this.model = model;
        this.owner = owner;
        this.purchaseDate = purchaseDate;
    }

    public String getSerialNumber(){
        return serialNumber;
    }

    public ScooterModel getModel() {
        return model;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public User getOwner() {
        return owner;
    }
}
