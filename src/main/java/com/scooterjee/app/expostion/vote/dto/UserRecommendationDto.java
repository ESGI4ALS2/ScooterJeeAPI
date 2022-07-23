package com.scooterjee.app.expostion.vote.dto;

import com.scooterjee.app.domain.problem.Problem;
import com.scooterjee.app.domain.user.User;

import javax.validation.constraints.NotNull;

public class UserRecommendationDto {
    @NotNull
    public Long problemId;

    public Problem problem;
    public Long referentId;
    public User user;
}
