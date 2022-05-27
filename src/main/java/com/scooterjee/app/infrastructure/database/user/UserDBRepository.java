package com.scooterjee.app.infrastructure.database.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDBRepository extends CrudRepository<UserDB, Long> {

    @Query("FROM UserDB WHERE mail = :mail")
    Optional<UserDB> getUserDBByMail(@Param("mail") String mail);
}
