package com.scooterjee.app.expostion.user.dto;

import javax.validation.constraints.NotBlank;

public class CreateAddressDTO {

    @NotBlank
    public String city;
    @NotBlank
    public String street;
    @NotBlank
    public String number;
    @NotBlank
    public String country;
    @NotBlank
    public String postalCode;
}
