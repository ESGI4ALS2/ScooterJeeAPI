package com.scooterjee.app.expostion.healthcheck.dto;

public class HealthCheckResponse {
    public final String status;

    public static HealthCheckResponse HealthCheckAlive() {
        return new HealthCheckResponse("Alive");
    }

    private HealthCheckResponse(String status) {
        this.status = status;
    }
}
