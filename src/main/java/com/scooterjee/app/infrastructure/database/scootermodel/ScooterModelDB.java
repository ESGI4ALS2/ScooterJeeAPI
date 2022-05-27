package com.scooterjee.app.infrastructure.database.scootermodel;

import com.scooterjee.app.domain.scootermodel.ScooterModel;

import javax.persistence.*;

@Table(name = "scooter_model")
@Entity
public class ScooterModelDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scooter_model_id")
    private Long scooterModelID;

    private String name;

    public ScooterModelDB(Long scooterModelID, String name) {
        this.scooterModelID = scooterModelID;
        this.name = name;
    }

    public Long getScooterModelID() {
        return scooterModelID;
    }

    public String getName() {
        return name;
    }

    public ScooterModelDB() {
    }

     static public ScooterModelDB of(ScooterModel scooterModel){
        return new ScooterModelDB(scooterModel.getID(), scooterModel.getName());
    }

    public ScooterModel toScooterModel(){
        return new ScooterModel(scooterModelID, name);
    }

}
