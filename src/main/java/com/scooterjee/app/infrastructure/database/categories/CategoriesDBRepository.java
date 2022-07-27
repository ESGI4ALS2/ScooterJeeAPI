package com.scooterjee.app.infrastructure.database.categories;

import com.scooterjee.app.infrastructure.database.problemstatus.ProblemStatusDB;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoriesDBRepository extends CrudRepository<CategoriesDB, Long> {

    @Query("FROM CategoriesDB WHERE name = :name")
    public Optional<CategoriesDB> getCategoriesDBSByName(@Param("name") String name);
}
