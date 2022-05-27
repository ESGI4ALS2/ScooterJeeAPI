package com.scooterjee.app.domain.problemestatus;

import com.scooterjee.kernel.Repository;

import java.util.Optional;

public interface ProblemStatusRepository extends Repository<ProblemStatus, Long> {


    Optional<ProblemStatus> getByName(String name);
}
