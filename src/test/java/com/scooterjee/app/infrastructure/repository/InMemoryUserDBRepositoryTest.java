package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.kernel.InMemoryRepositoryTest;
import com.scooterjee.kernel.Repository;
import com.scooterjee.kernel.email.EmailAddress;

import java.util.ArrayList;
import java.util.List;

class InMemoryUserDBRepositoryTest extends InMemoryRepositoryTest<User, Long> {


    static Address defaultValidAddress = new Address(1L, "", "", "", "", "", 0.0, 0.0);

    public InMemoryUserDBRepositoryTest() {
        super( new User(1L, "bob", "bob", "p", "0601020304", new EmailAddress("test@test.com"), defaultValidAddress),
                new User(2L, "bob", "bob", "p", "0601020304", new EmailAddress(""), defaultValidAddress));
    }

    @Override
    protected Repository<User, Long> getNewRepository(List<User> values) {
        return new InMemoryUserRepository(new ArrayList<>(values));
    }

    @Override
    protected User getUpdateValue1() {
        return new User(1L,  "bob2", "bob2", "p", "0601020304",new EmailAddress("test@test.com"), defaultValidAddress);
    }
}
