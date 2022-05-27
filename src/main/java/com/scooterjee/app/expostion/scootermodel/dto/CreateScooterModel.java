package com.scooterjee.app.expostion.scootermodel.dto;

import javax.validation.constraints.NotBlank;

public class CreateScooterModel {
    @NotBlank
    public String name;

    public CreateScooterModel() {

    }

    public CreateScooterModel(String name) {
        this.name = name;
    }
}
