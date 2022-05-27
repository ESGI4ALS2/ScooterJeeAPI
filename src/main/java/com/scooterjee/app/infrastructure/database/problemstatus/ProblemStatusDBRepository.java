package com.scooterjee.app.infrastructure.database.problemstatus;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProblemStatusDBRepository extends CrudRepository<ProblemStatusDB, Long> {

    @Query("FROM ProblemStatusDB WHERE name = :name")
    public Optional<ProblemStatusDB> getProblemStatusDBByName(@Param("name") String name);
}
