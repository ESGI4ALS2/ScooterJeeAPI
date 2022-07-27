package com.scooterjee.app.infrastructure.service.recommendation;

import com.scooterjee.app.domain.problemestatus.ProblemStatus;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.vote.UserRecommendation;
import com.scooterjee.app.domain.vote.Vote;
import com.scooterjee.app.domain.vote.VoteRepository;
import com.scooterjee.app.infrastructure.service.UserService;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RecommendUserService extends SimpleService<VoteRepository, Vote, Long> {

    private final UserService userService;
    private final VoteTypeService voteTypeService;

    @Autowired
    public RecommendUserService(
        VoteTypeService voteTypeService,
        VoteRepository repository,
        Validator<Vote> validator,
        UserService userService
    ) {
        super(repository, validator, "vote");
        this.userService = userService;
        this.voteTypeService = voteTypeService;
    }

    public void recommendUser(UserRecommendation userRecommendation) {
        User referent = userService.getById(userRecommendation.referentId);
        if(!userRecommendation.problem.getReferent().equals(referent)) {
            throw new IllegalArgumentException("Unknown referent");
        }

        voteTypeService.add(userRecommendation.voteType);

        if(!userRecommendation.problem.getStatus().getName().equals(ProblemStatus.STATUS_WAITING_FOR_REVIEW)) {
            throw new IllegalArgumentException(
                "Recommendation is only available for problems in status " + ProblemStatus.STATUS_WAITING_FOR_REVIEW
            );
        }

        Vote recommendation = new Vote(
            null,
            LocalDate.now(),
            userRecommendation.user,
            referent,
            userRecommendation.voteType
        );

        this.repository.add(recommendation);
    }

}
