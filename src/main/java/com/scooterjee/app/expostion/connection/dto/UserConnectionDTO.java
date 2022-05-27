package com.scooterjee.app.expostion.connection.dto;

import javax.validation.constraints.NotBlank;

public class UserConnectionDTO {
    @NotBlank
    public String email;
    @NotBlank
    public String password;

    public UserConnectionDTO(){

    }
}
