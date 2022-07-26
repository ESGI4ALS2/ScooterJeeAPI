package com.scooterjee.app.expostion.healthcheck.controller;

import com.scooterjee.app.expostion.healthcheck.dto.HealthCheckResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping(value = "/")
    public HealthCheckResponse healthcheck() {
        return HealthCheckResponse.HealthCheckAlive();
    }
}
