package com.scooterjee.app.expostion.vote.controller;

import com.scooterjee.app.domain.problem.Problem;
import com.scooterjee.app.domain.session.Session;
import com.scooterjee.app.domain.user.exception.OperationNotPermittedException;
import com.scooterjee.app.domain.vote.UserRecommendation;
import com.scooterjee.app.domain.vote_type.VoteType;
import com.scooterjee.app.expostion.error.ErrorHandler;
import com.scooterjee.app.expostion.vote.dto.UserRecommendationDto;
import com.scooterjee.app.infrastructure.service.ProblemService;
import com.scooterjee.app.infrastructure.service.SessionService;
import com.scooterjee.app.infrastructure.service.recommendation.RecommendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class VoteController extends ErrorHandler {

    private final RecommendUserService recommendationService;
    private final SessionService sessionService;
    private final ProblemService problemService;

    @Autowired
    public VoteController(
        RecommendUserService recommendationService,
        SessionService sessionService,
        ProblemService problemService
    ) {
        this.recommendationService = recommendationService;
        this.sessionService = sessionService;
        this.problemService = problemService;
    }

    @PostMapping(value = "/user/{userId}/recommend")
    public ResponseEntity<String> recommendUser(
        @RequestHeader("uuid") UUID connectedUserId,
        @PathVariable long userId,
        @RequestBody @Valid UserRecommendationDto userRecommendationDto
    ) {
        Session userSession = sessionService.get(connectedUserId.toString());
        Problem problem = problemService.get(userRecommendationDto.problemId);

        if(!problem.getCreatedBy().equals(userSession.getUser())) {
            throw new OperationNotPermittedException("User is not the creator of this problem");
        }

        UserRecommendation userRecommendation = new UserRecommendation();

        userRecommendation.problem = problem;
        userRecommendation.referentId = userId;
        userRecommendation.user = userSession.getUser();
        userRecommendation.voteType = VoteType.UP_VOTE;

        recommendationService.recommendUser(userRecommendation);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = "/user/{userId}/dont-recommend")
    public ResponseEntity<String> doNotRecommendUser(
        @RequestHeader("uuid") UUID connectedUserId,
        @PathVariable long userId,
        @RequestBody @Valid UserRecommendationDto userRecommendationDto
    ) {
        Session userSession = sessionService.get(connectedUserId.toString());
        Problem problem = problemService.get(userRecommendationDto.problemId);

        if(!problem.getCreatedBy().equals(userSession.getUser())) {
            throw new OperationNotPermittedException("User is not the creator of this problem");
        }
        UserRecommendation userRecommendation = new UserRecommendation();

        userRecommendation.problem = problem;
        userRecommendation.referentId = userId;
        userRecommendation.user = userSession.getUser();
        userRecommendation.voteType = VoteType.DOWN_VOTE;

        recommendationService.recommendUser(userRecommendation);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
