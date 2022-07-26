package com.scooterjee.app.infrastructure.database.scooter;

import com.scooterjee.app.domain.scooter.Scooter;
import com.scooterjee.app.infrastructure.database.scootermodel.ScooterModelDB;
import com.scooterjee.app.infrastructure.database.user.UserDB;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "scooter")
@Entity
public class ScooterDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scooter_id")
    private Long scooterID;

    private String serialNumber;

    private LocalDate dateOfPurchase;

    @OneToOne
    private ScooterModelDB scooterModel;

    @OneToOne(fetch = FetchType.LAZY)
    private UserDB user;

    public ScooterDB() {
    }

    public ScooterDB(
        Long scooterID,
        String serialNumber,
        LocalDate dateOfPurchase,
        ScooterModelDB scooterModel,
        UserDB user
    ) {
        this.scooterID = scooterID;
        this.serialNumber = serialNumber;
        this.dateOfPurchase = dateOfPurchase;
        this.scooterModel = scooterModel;
        this.user = user;
    }

    public ScooterDB(LocalDate dateOfPurchase, ScooterModelDB scooterModel) {
        this.dateOfPurchase = dateOfPurchase;
        this.scooterModel = scooterModel;
    }

    public Long getScooterID() {
        return scooterID;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public ScooterModelDB getScooterModel() {
        return scooterModel;
    }

    public UserDB getUser() {
        return user;
    }

    public static ScooterDB of(Scooter scooter) {
        return new ScooterDB(
            scooter.getID(),
            scooter.getSerialNumber(),
            scooter.getPurchaseDate(),
            ScooterModelDB.of(scooter.getModel()),
            UserDB.of(scooter.getOwner())
        );
    }

    public Scooter toScooter() {
        return new Scooter(
            scooterID,
            serialNumber,
            scooterModel.toScooterModel(),
            user.toUser(),
            dateOfPurchase
        );
    }

}
