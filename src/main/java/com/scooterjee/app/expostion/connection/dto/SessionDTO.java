package com.scooterjee.app.expostion.connection.dto;

import javax.validation.constraints.NotBlank;

public class SessionDTO {
    @NotBlank
    public String token;

    public SessionDTO(){

    }

    public SessionDTO(String token) {
        this.token = token;
    }
}
