package com.scooterjee.app.infrastructure.service.recommendation;

import com.scooterjee.app.domain.problemestatus.ProblemStatus;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.vote.Vote;
import com.scooterjee.app.domain.vote.VoteRepository;
import com.scooterjee.app.domain.vote_type.VoteType;
import com.scooterjee.app.expostion.vote.dto.UserRecommendationDto;
import com.scooterjee.app.infrastructure.service.UserService;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RecommendUserService extends SimpleService<VoteRepository, Vote, Long> {

    private final UserService userService;

    @Autowired
    public RecommendUserService(
            VoteRepository repository,
            Validator<Vote> validator,
            UserService userService
    ) {
        super(repository, validator, "vote");
        this.userService = userService;
    }

    public void recommendUser(UserRecommendationDto userRecommendationDto) {
        User referent = userService.getById(userRecommendationDto.problemId);
        if(!userRecommendationDto.problem.getReferent().equals(referent)) {
            throw new IllegalArgumentException("Unknown referent");
        }

        if(!userRecommendationDto.problem.getStatus().getName().equals(ProblemStatus.STATUS_WAITING_FOR_REVIEW)) {
            throw new IllegalArgumentException(
                "Recommendation is only available for problems in status " + ProblemStatus.STATUS_WAITING_FOR_REVIEW
            );
        }

        Vote recommendation = new Vote(
            null,
            LocalDate.now(),
            userRecommendationDto.user,
            referent,
            userRecommendationDto.voteType
        );

        this.repository.add(recommendation);
    }

    public float getUserNote(Long userId) {
        User user = userService.getById(userId);
        return user.getVotesReceived().stream()
            .map(vote -> {
                int sign = vote.getType().equals(VoteType.UP_VOTE) ? 1 : -1;
                LocalDate dateOfVote = vote.getVoteDate();
                // si la date est >= à 1 mois : 1 vote vaut 0,75, >= 3 mois : 1 vote vaut 0,5 et >= 6 mois : 1 vote vaut 0,25.
                if(dateOfVote.isAfter(LocalDate.now().minusMonths(1))) {
                    return 1f * sign;
                } else if(dateOfVote.isAfter(LocalDate.now().minusMonths(3))) {
                    return 0.75f * sign;
                } else if(dateOfVote.isAfter(LocalDate.now().minusMonths(6))) {
                    return 0.5f * sign;
                } else {
                    return 0.25f * sign;
                }

            })
            .reduce(Float::sum)
            .orElse(0f);
    }


}
