package com.scooterjee.app.infrastructure.service;

import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.user.UserRepository;
import com.scooterjee.app.domain.user.exception.UserAlreadyExistsException;
import com.scooterjee.app.expostion.user.dto.ReferentResponse;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import com.scooterjee.kernel.email.EmailAddress;
import com.scooterjee.kernel.exception.SimpleServiceObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends SimpleService<UserRepository, User, Long> {

    public UserService(UserRepository repository, Validator<User> validator) {
        super(repository, validator, "user");
    }

    @Override
    public Long add(User value) {
        if (repository.getByEmail(value.getEmailAddress()).isPresent()) {
            throw new UserAlreadyExistsException(value.getEmailAddress().toString());
        }
        return super.add(value);
    }

    public User getByEmail(EmailAddress emailAddress){
        return repository
            .getByEmail(emailAddress)
            .orElseThrow(() -> new SimpleServiceObjectNotFoundException("user", emailAddress.toString())
        );
    }

    public User getById(Long userId){
        return repository
            .get(userId)
            .orElseThrow(() -> new SimpleServiceObjectNotFoundException("user", userId.toString())
        );
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

    public List<ReferentResponse> getReferents() {
        return repository.getAll().stream()
            .map(user -> new ReferentResponse(
                user.getID(),
                user.getLastName(),
                user.getFirstName(),
                user.getRating()
            ))
            .collect(Collectors.toList());
    }
}
