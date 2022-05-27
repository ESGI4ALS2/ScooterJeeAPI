package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.user.UserRepository;
import com.scooterjee.kernel.InMemoryRepository;
import com.scooterjee.kernel.email.EmailAddress;

import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository extends InMemoryRepository<User, Long> implements UserRepository {

    public InMemoryUserRepository(List<User> values) {
        super(values);
    }

    public InMemoryUserRepository() {
    }

    @Override
    public Optional<User> getByEmail(EmailAddress emailAddress) {
        return Optional.empty();
    }

    @Override
    public boolean addCategoryToUser(EmailAddress emailAddress, Long categoryID) {
        return false;
    }

    @Override
    public boolean addRoleToUser(EmailAddress emailAddress, Long roleID) {
        return false;
    }

    @Override
    public boolean removeRoleToUser(EmailAddress emailAddress, Long roleID) {
        return false;
    }
}
