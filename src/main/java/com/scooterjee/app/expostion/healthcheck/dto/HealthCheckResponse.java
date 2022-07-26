package com.scooterjee.app.expostion.healthcheck.dto;

import java.util.UUID;

public class HealthCheckResponse {
    public static UUID appUUID = UUID.randomUUID();
    public final String status;

    public static HealthCheckResponse HealthCheckAlive() {
        return new HealthCheckResponse("Alive : " + appUUID);
    }

    private HealthCheckResponse(String status) {
        this.status = status;
    }
}
