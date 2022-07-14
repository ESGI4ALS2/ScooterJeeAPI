package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.user.UserRepository;
import com.scooterjee.app.infrastructure.database.categories.CategoriesDB;
import com.scooterjee.app.infrastructure.database.categories.CategoriesDBRepository;
import com.scooterjee.app.infrastructure.database.role.RoleDB;
import com.scooterjee.app.infrastructure.database.role.RoleDBRepository;
import com.scooterjee.app.infrastructure.database.user.UserDB;
import com.scooterjee.app.infrastructure.database.user.UserDBRepository;
import com.scooterjee.kernel.email.EmailAddress;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InDBUserRepository implements UserRepository {

    private final UserDBRepository dbRepository;

    private final CategoriesDBRepository categoriesDBRepository;

    private final RoleDBRepository roleDBRepository;

    public InDBUserRepository(
        UserDBRepository dbRepository,
        CategoriesDBRepository categoriesDBRepository,
        RoleDBRepository roleDBRepository
    ) {
        this.dbRepository = dbRepository;
        this.categoriesDBRepository = categoriesDBRepository;
        this.roleDBRepository = roleDBRepository;
    }

    public User getByEmail(String email) {
        UserDB userDB = this.dbRepository.getUserDBByMail(email).orElseThrow();
        return userDB.toUser();
    }

    @Override
    public Optional<User> get(Long key) {
        return Optional.empty();
    }

    @Override
    public Long add(User value) {
        UserDB userDB = dbRepository.save(UserDB.of(value));
        value.setId(userDB.getId());
        return userDB.getId();
    }

    @Override
    public boolean update(User value) {
        return false;
    }

    @Override
    public boolean remove(Long value) {
        return false;
    }

    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        dbRepository.findAll().forEach(userDB -> result.add(userDB.toUser()));
        return result;
    }

    @Override
    public Optional<User> getByEmail(EmailAddress emailAddress) {
        Optional<UserDB> userDB = dbRepository.getUserDBByMail(emailAddress.toString());
        if(userDB.isEmpty()){
            return Optional.empty();
        }

        return Optional.of( userDB.get().toUser());
    }

    @Override
    public boolean addCategoryToUser(EmailAddress emailAddress, Long categoryID) {
        Optional<UserDB> userDB = dbRepository.getUserDBByMail(emailAddress.toString());

        if(userDB.isEmpty()){
            return false;
        }

        Optional<CategoriesDB> categoriesDB =  categoriesDBRepository.findById(categoryID);

        if(categoriesDB.isEmpty()){
            return false;
        }

        userDB.get().getCategories().add(categoriesDB.get());

        UserDB userDB1 = dbRepository.save(userDB.get());
        return true;
    }

    @Override
    public boolean addRoleToUser(EmailAddress emailAddress, Long roleID) {
        Optional<UserDB> userDB = dbRepository.getUserDBByMail(emailAddress.toString());

        if(userDB.isEmpty()){
            return false;
        }

        Optional<RoleDB> roleDB =  roleDBRepository.findById(roleID);

        if(roleDB.isEmpty()){
            return false;
        }

        userDB.get().getRoles().add(roleDB.get());

        UserDB userDB1 = dbRepository.save(userDB.get());
        return true;
    }

    @Override
    public boolean removeRoleToUser(EmailAddress emailAddress, Long roleID) {
        Optional<UserDB> userDB = dbRepository.getUserDBByMail(emailAddress.toString());

        if(userDB.isEmpty()){
            return false;
        }

        Optional<RoleDB> roleDB =  roleDBRepository.findById(roleID);

        if(roleDB.isEmpty()){
            return false;
        }

        userDB.get().getRoles().remove(roleDB.get());

        UserDB userDB1 = dbRepository.save(userDB.get());
        return true;
    }
}
