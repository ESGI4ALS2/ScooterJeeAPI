package com.scooterjee.app.domain.scooter;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.address.AddressValidator;
import com.scooterjee.app.domain.scooter.exception.InvalidScooterException;
import com.scooterjee.app.domain.scootermodel.ScooterModel;
import com.scooterjee.app.domain.scootermodel.ScooterModelValidator;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.user.UserValidator;
import com.scooterjee.kernel.email.EmailAddress;
import com.scooterjee.kernel.email.SimpleEmailAddressValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ScooterValidatorTest {

    static ScooterValidator scooterValidator;
    public static User defaultValidUser;

    @BeforeAll
    static void beforeAll() {
        scooterValidator = new ScooterValidator(new ScooterModelValidator(), new UserValidator(new AddressValidator(), new SimpleEmailAddressValidator()));
        defaultValidUser = new User(1L, "bob", "bob", "p",
                "0601020304",
                new EmailAddress("test@test.com"),
                new Address(1L, "", "", "", "", "", 0.0, 0.0));
    }

    @Test
    void should_be_valid() {
        Scooter scooter = new Scooter(1L, "s", new ScooterModel(1L, "model1"), defaultValidUser, LocalDate.now());
        Assertions.assertDoesNotThrow(() -> scooterValidator.validate(scooter));
    }

    @Test
    void should_not_be_valid_with_null_serialID() {
        Scooter scooter = new Scooter(1L, null, new ScooterModel(1L, "model1"), defaultValidUser, LocalDate.now());
        Assertions.assertThrows(InvalidScooterException.class, () -> scooterValidator.validate(scooter));
    }

    @Test
    void should_not_be_valid_with_empty_serialID() {
        Scooter scooter = new Scooter(1L, "", new ScooterModel(1L, "model1"), defaultValidUser, LocalDate.now());
        Assertions.assertThrows(InvalidScooterException.class, () -> scooterValidator.validate(scooter));
    }

    @Test
    void should_not_be_valid_with_null_model() {
        Scooter scooter = new Scooter(1L, "s", null, defaultValidUser, LocalDate.now());
        Assertions.assertThrows(InvalidScooterException.class, () -> scooterValidator.validate(scooter));
    }

    @Test
    void should_not_be_valid_with_null_purchase_date() {
        Scooter scooter = new Scooter(1L, "s", new ScooterModel(1L, "model1"), defaultValidUser, null);
        Assertions.assertThrows(InvalidScooterException.class, () -> scooterValidator.validate(scooter));
    }

    @Test
    void should_not_be_valid_with_null_owner() {
        Scooter scooter = new Scooter(1L, "s", new ScooterModel(1L, "model1"), null, null);
        Assertions.assertThrows(InvalidScooterException.class, () -> scooterValidator.validate(scooter));
    }

}
