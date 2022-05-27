package com.scooterjee.app.infrastructure.database.problems;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProblemsDBRepository extends CrudRepository<ProblemsDB, Long> {

    @Query("FROM ProblemsDB WHERE categories.categoriesID = :categoryID  AND referent IS NULL")
    List<ProblemsDB> getProblemAvailableForUser(@Param("categoryID") Long categoryID);

}
