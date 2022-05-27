package com.scooterjee.app.domain.user;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.address.AddressValidator;
import com.scooterjee.app.domain.user.exception.InvalidUserException;
import com.scooterjee.kernel.email.EmailAddress;
import com.scooterjee.kernel.email.SimpleEmailAddressValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserValidatorTest {

    static UserValidator userValidator;
    static EmailAddress defaultValidEmailAddress;
    static String defaultValidFirstName;
    static String defaultValidLastName;
    static String defaultValidPassword;
    static String defaultValidPhoneNumber;
    static Address defaultValidAddress;

    @BeforeAll
    static void beforeAll(){
        userValidator = new UserValidator( new AddressValidator(), new SimpleEmailAddressValidator());
        defaultValidEmailAddress = new EmailAddress("test@test.com");
        defaultValidFirstName = "Bob";
        defaultValidLastName = "Marley";
        defaultValidPassword = "p";
        defaultValidPhoneNumber = "0601020304";
        defaultValidAddress = new Address(1L, "", "", "", "", "", 0.0, 0.0);
    }

    @Test
    void should_be_valid(){
        User user = new User(1L,  defaultValidFirstName, defaultValidLastName, defaultValidPassword, defaultValidPhoneNumber, defaultValidEmailAddress, defaultValidAddress);
        Assertions.assertDoesNotThrow(() -> userValidator.validate(user));
    }

    @Test
    void should_not_be_valid_with_null_email_address(){
        User user = new User(1L,  defaultValidFirstName, defaultValidLastName, defaultValidPassword, defaultValidPhoneNumber, null, defaultValidAddress);
        Assertions.assertThrows(InvalidUserException.class, () -> userValidator.validate(user));
    }

    @Test
    void should_not_be_valid_with_empty_email_address(){
        User user = new User(1L, defaultValidFirstName, defaultValidLastName, defaultValidPassword, defaultValidPhoneNumber, new EmailAddress(""), defaultValidAddress);
        Assertions.assertThrows(InvalidUserException.class, () -> userValidator.validate(user));
    }

    @Test
    void should_not_be_valid_with_null_first_name(){
        User user = new User(1L, null, defaultValidLastName, defaultValidPassword, defaultValidPhoneNumber, defaultValidEmailAddress, defaultValidAddress);
        Assertions.assertThrows(InvalidUserException.class, () -> userValidator.validate(user));
    }

    @Test
    void should_not_be_valid_with_empty_first_name(){
        User user = new User(1L,  "", defaultValidLastName, defaultValidPassword, defaultValidPhoneNumber, defaultValidEmailAddress, defaultValidAddress);
        Assertions.assertThrows(InvalidUserException.class, () -> userValidator.validate(user));
    }

    @Test
    void should_not_be_valid_with_null_last_name(){
        User user = new User(1L, defaultValidFirstName, null, defaultValidPassword, defaultValidPhoneNumber, defaultValidEmailAddress, defaultValidAddress);
        Assertions.assertThrows(InvalidUserException.class, () -> userValidator.validate(user));
    }

    @Test
    void should_not_be_valid_with_empty_last_name(){
        User user = new User(1L,  defaultValidFirstName, "", defaultValidPassword, defaultValidPhoneNumber, defaultValidEmailAddress,defaultValidAddress);
        Assertions.assertThrows(InvalidUserException.class, () -> userValidator.validate(user));
    }

    @Test
    void should_not_be_valid_with_null_password(){
        User user = new User(1L,  defaultValidFirstName, defaultValidLastName, null, defaultValidPhoneNumber, defaultValidEmailAddress,defaultValidAddress);
        Assertions.assertThrows(InvalidUserException.class, () -> userValidator.validate(user));
    }

    @Test
    void should_not_be_valid_with_empty_password(){
        User user = new User(1L,  defaultValidFirstName, defaultValidLastName, "", defaultValidPhoneNumber, defaultValidEmailAddress,defaultValidAddress);
        Assertions.assertThrows(InvalidUserException.class, () -> userValidator.validate(user));
    }

    @Test
    void should_not_be_valid_with_null_address(){
        User user = new User(1L,  defaultValidFirstName, defaultValidLastName, defaultValidPassword, defaultValidPhoneNumber, defaultValidEmailAddress,null);
        Assertions.assertThrows(InvalidUserException.class, () -> userValidator.validate(user));
    }

}
