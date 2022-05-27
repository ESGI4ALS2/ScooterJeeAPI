package com.scooterjee.app.expostion.scooter.dto;

public class CreateScooterWithModelDTO {
    public Long id;
    public String serialNumber;
    public String model;

    public CreateScooterWithModelDTO() {
    }

    public CreateScooterWithModelDTO(Long id, String serialNumber, String model) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.model = model;
    }
}
