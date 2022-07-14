package com.scooterjee.app.infrastructure.service.recommendation;

import org.springframework.stereotype.Service;

@Service
public class RecommendUserService {

    public void recommendUser(Long userId) {
        System.out.println("Recommend user " + userId);
    }

    public void doNotRecommendUser(Long userId) {
        System.out.println("Dont recommend user " + userId);
    }

}
