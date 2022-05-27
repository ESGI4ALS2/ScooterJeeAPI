package com.scooterjee.app.expostion.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateUserDTO {

    @NotBlank
    public String email;
    @NotBlank
    public String lastname;
    @NotBlank
    public String firstname;
    @NotBlank
    public String password;
    @NotBlank
    public String phoneNumber;
    @NotNull
    public CreateAddressDTO address;
}
