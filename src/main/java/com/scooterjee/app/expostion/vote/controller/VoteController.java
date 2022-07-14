package com.scooterjee.app.expostion.vote.controller;

import com.scooterjee.app.expostion.error.ErrorHandler;
import com.scooterjee.app.infrastructure.service.recommendation.RecommendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class VoteController extends ErrorHandler {

    private final RecommendUserService recommendationService;

    @Autowired
    public VoteController(RecommendUserService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping(value = "/user/{userId}/recommend")
    public void recommendUser(@PathVariable @Valid long userId) {
        recommendationService.recommendUser(userId);
    }

    @PostMapping(value = "/user/{userId}/dont-recommend")
    public void doNotRecommendUser(@PathVariable @Valid long userId) {
        recommendationService.doNotRecommendUser(userId);
    }
}
