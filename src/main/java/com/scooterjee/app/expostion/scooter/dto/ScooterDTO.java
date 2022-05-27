package com.scooterjee.app.expostion.scooter.dto;

public class ScooterDTO {
    public Long id;
    public Long scooterModelID;
    public String serialNumber;

    public ScooterDTO() {

    }

    public ScooterDTO(Long id, Long scooterModelID, String serialNumber) {
        this.id = id;
        this.scooterModelID = scooterModelID;
        this.serialNumber = serialNumber;
    }
}
