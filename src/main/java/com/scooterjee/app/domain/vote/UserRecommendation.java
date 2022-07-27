package com.scooterjee.app.domain.vote;

import com.scooterjee.app.domain.problem.Problem;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.vote_type.VoteType;

public class UserRecommendation {
    public Problem problem;
    public Long referentId;
    public User user;
    public VoteType voteType;
}
