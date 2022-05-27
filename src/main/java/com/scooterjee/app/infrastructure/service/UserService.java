package com.scooterjee.app.infrastructure.service;

import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.user.UserRepository;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import com.scooterjee.kernel.email.EmailAddress;
import com.scooterjee.kernel.exception.SimpleServiceObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends SimpleService<UserRepository, User, Long> {

    public UserService(UserRepository repository, Validator<User> validator) {
        super(repository, validator, "user");
    }

    public User getByEmail(EmailAddress emailAddress){
        return repository.getByEmail(emailAddress).orElseThrow(() -> new SimpleServiceObjectNotFoundException("user", emailAddress.toString()));
    }

    public void addCategoryToUser(EmailAddress userEmail, Long categoryID){
        if( !repository.addCategoryToUser(userEmail, categoryID) ){
            throw new SimpleServiceObjectNotFoundException("", userEmail.toString() + " | " +categoryID.toString());
        }
    }

    public void addRoleToUser(EmailAddress userEmail, Long roleID){
        repository.addRoleToUser(userEmail, roleID);
    }

    public void removeRoleToUser(EmailAddress userEmail, Long roleID) {
        repository.removeRoleToUser(userEmail, roleID);
    }
}
