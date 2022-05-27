package com.scooterjee.app.domain.problem;

import com.scooterjee.kernel.Repository;

import java.util.List;

public interface ProblemRepository extends Repository<Problem, Long> {

    List<Problem> getAllAvailableProblem(Long categoryID);
}
