package com.scooterjee.app.infrastructure.service;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.address.AddressValidator;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.user.UserRepository;
import com.scooterjee.app.domain.user.UserValidator;
import com.scooterjee.app.infrastructure.repository.InMemoryUserRepository;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.SimpleServiceTest;
import com.scooterjee.kernel.Validator;
import com.scooterjee.kernel.email.EmailAddress;
import com.scooterjee.kernel.email.SimpleEmailAddressValidator;

import java.util.List;

class UserServiceTest extends SimpleServiceTest<UserRepository, User, Long> {

    static Address defaultValidAddress = new Address(1L, "", "", "", "", "", 0.0, 0.0);

    public UserServiceTest() {
        super(new User(1L,  "bob", "bob", "p", "0601020304", new EmailAddress("test@test.com"), defaultValidAddress),
                new User(2L,  "bob", "bob", "p", "0601020304", new EmailAddress("test@test.com"), defaultValidAddress));
    }

    @Override
    protected Validator<User> getNewValidator() {
        return new UserValidator(new AddressValidator(), new SimpleEmailAddressValidator());
    }

    @Override
    protected UserRepository getNewRepository(List<User> values) {
        return new InMemoryUserRepository(values);
    }

    @Override
    protected UserRepository getNewRepository() {
        return new InMemoryUserRepository();
    }

    @Override
    protected SimpleService<UserRepository, User, Long> getNewService() {
        return new UserService(getNewRepository(), (UserValidator) getNewValidator());
    }

    @Override
    protected SimpleService<UserRepository, User, Long> getNewService(UserRepository repo, Validator<User> validator) {
        return new UserService(repo, (UserValidator) validator);
    }
}
