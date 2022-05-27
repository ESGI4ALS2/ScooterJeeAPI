package com.scooterjee.app.domain.user;

import com.scooterjee.kernel.Repository;
import com.scooterjee.kernel.email.EmailAddress;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> getByEmail(EmailAddress emailAddress);

    boolean addCategoryToUser(EmailAddress emailAddress, Long categoryID);

    boolean addRoleToUser(EmailAddress emailAddress, Long roleID);

    boolean removeRoleToUser(EmailAddress emailAddress, Long roleID);
}
